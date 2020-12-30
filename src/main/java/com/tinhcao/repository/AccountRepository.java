package com.tinhcao.repository;

import com.tinhcao.model.Account;

import java.io.IOException;
import java.util.List;

public interface AccountRepository {
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
     * @return Created Account
     */
    Account createAccount(Account account) throws IOException;

    /**
     * Check exist account
     *
     * @param id account id
     * @return true if given id account exist and vice versa
     * @throws IOException throw error when can not read file to check exist account
     */
    boolean isAccountExist(long id) throws IOException;


}
