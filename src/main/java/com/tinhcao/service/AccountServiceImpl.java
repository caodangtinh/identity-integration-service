package com.tinhcao.service;

import com.tinhcao.model.Account;
import com.tinhcao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(long id) throws IOException {
        return accountRepository.getAccount(id);
    }

    @Override
    public Account updateAccount(Account account) throws IOException {
        return accountRepository.updateAccount(account);
    }

    @Override
    public List<Account> listAllAccount() throws IOException {
        return accountRepository.listAllAccount();
    }

    @Override
    public Account createAccount(Account account) throws IOException {
        return accountRepository.createAccount(account);
    }

    @Override
    public boolean isAccountExist(long id) throws IOException {
        return accountRepository.isAccountExist(id);
    }
}
