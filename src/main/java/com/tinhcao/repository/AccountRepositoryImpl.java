package com.tinhcao.repository;

import com.tinhcao.utils.JsonUtils;
import com.tinhcao.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository(value = "accountRepository")
public class AccountRepositoryImpl implements AccountRepository {

    private JsonUtils jsonUtils;

    @Autowired
    public void setJsonUtils(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @Override
    public Account getAccount(long id) throws IOException {
        List<Account> accountList = listAllAccount();
        Optional<Account> matchingObjects = accountList.stream().
                filter(p -> p.getCustomerId() == id).
                findFirst();
        return matchingObjects.orElse(null);
    }

    @Override
    public Account updateAccount(Account account) throws IOException {
        List<Account> accountList = listAllAccount();
        accountList.removeIf(a -> a.getCustomerId() == account.getCustomerId());
        accountList.add(account);
        jsonUtils.writeJsonToFile(accountList);
        return account;

    }

    @Override
    public List<Account> listAllAccount() throws IOException {
        return jsonUtils.getListAccountFromJsonFile();
    }

    @Override
    public Account createAccount(Account account) throws IOException {
        List<Account> accountList = listAllAccount();
        accountList.add(account);
        jsonUtils.writeJsonToFile(accountList);
        return account;
    }

    @Override
    public boolean isAccountExist(long id) throws IOException {
        return getAccount(id) != null;
    }
}
