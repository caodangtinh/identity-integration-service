package com.tinhcao.service;

import com.tinhcao.model.Account;

import java.io.IOException;
import java.util.List;

public interface AccountService {

    /**
     * Get Account detail by account id
     *
     * @param id account id
     * @return succeed created account
     */
    Account getAccount(long id) throws IOException;

    /**
     * Update account by given id and Account
     *
     * @param account Account updated information
     * @return succeed updated account
     */
    Account updateAccount(Account account) throws IOException;

    /**
     * List all account
     *
     * @return List Account
     */
    List<Account> listAllAccount() throws IOException;

    /**
     * Create new account
     *
     * @param account Account information to created
     * @return Account
     */
    Account createAccount(Account account) throws IOException;

    /**
     * Check exist account
     *
     * @param id account id
     * @return true if account exist and vice versa
     * @throws IOException throw error when can not get list account from file
     */
    boolean isAccountExist(long id) throws IOException;
}
