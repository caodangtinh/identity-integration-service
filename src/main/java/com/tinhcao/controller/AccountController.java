package com.tinhcao.controller;

import com.tinhcao.exception.AccountException;
import com.tinhcao.json.AccountRequestModel;
import com.tinhcao.model.Account;
import com.tinhcao.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.List;

@RestController(value = "accountController")
@RequestMapping(value = "/v1/accounts/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = {""})
    @ResponseBody
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAccount() {
        try {
            List<Account> accounts = accountService.listAllAccount();
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new AccountException("Error at getListAccount() method " +
                    e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = {"{account_id}"})
    @ResponseBody
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(@PathVariable("account_id") long account_id) {
        logger.info("Fetching Account with account_id {}", account_id);
        try {
            Account account = accountService.getAccount(account_id);
            if (account == null) {
                logger.error("Account with account_id {} not found.", account_id);
                return new ResponseEntity<>(new AccountException("Account with account_id " + account_id + " not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new AccountException("Error at getAccount() method " +
                    e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {""})
    @ResponseBody
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    @Consumes(value = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestModel accountRequestModel, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Account : {}", accountRequestModel);
        try {
            if (accountService.isAccountExist(accountRequestModel.getCustomerId())) {
                logger.error("Unable to create Account with id {} already exist", accountRequestModel.getCustomerId());
                return new ResponseEntity<>(new AccountException("Unable to create. A Account with name " +
                        accountRequestModel.getCustomerId() + " already exist."), HttpStatus.CONFLICT);
            }
            Account addAccount = new Account.Builder()
                    .customerId(accountRequestModel.getCustomerId())
                    .customerName(accountRequestModel.getCustomerName())
                    .amount(accountRequestModel.getAmount())
                    .currency(accountRequestModel.getCurrency()).build();
            accountService.createAccount(addAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/v1/accounts/accountRequestModel/{account_id}").buildAndExpand(accountRequestModel.getCustomerId()).toUri());
            return new ResponseEntity<>("Successful create Account ", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new AccountException("Error when creating Account with id {} " +
                    accountRequestModel.getCustomerId()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping(value = {"{account_id}"})
    @ResponseBody
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    @Consumes(value = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@PathVariable("account_id") long account_id, @RequestBody AccountRequestModel accountRequestModel) {
        logger.info("Updating Account with account_id {}", account_id);
        try {
            if (accountService.getAccount(account_id) == null) {
                logger.error("Unable to update. Account with id {} not found.", account_id);
                return new ResponseEntity<>(new AccountException("Account with account_id " + account_id + " not found"), HttpStatus.NOT_FOUND);
            }
            Account updateAccount = new Account.Builder()
                    .customerId(account_id)
                    .customerName(accountRequestModel.getCustomerName())
                    .amount(accountRequestModel.getAmount())
                    .currency(accountRequestModel.getCurrency()).build();
            Account account = accountService.updateAccount(updateAccount);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new AccountException("Error at updateAccount() method " +
                    e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
