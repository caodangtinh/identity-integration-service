package com.tinhcao.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tinhcao.json.AccountRequestModel;
import com.tinhcao.model.Account;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "jsonUtils")
public class JsonUtils {
    private static final File ACCOUNT_JSON_FILE = new File("account.json");

    /**
     * Read json file and return list Account
     *
     * @return List Account
     * @throws IOException IOException
     */
    public List<Account> getListAccountFromJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<AccountRequestModel>> typeReference = new TypeReference<List<AccountRequestModel>>() {
        };
        InputStream inputStream = new FileInputStream(ACCOUNT_JSON_FILE);
        List<AccountRequestModel> accountRequestModels = mapper.readValue(inputStream, typeReference);
        List<Account> accountList = accountRequestModels
                .stream()
                .map(model -> new Account.Builder()
                        .customerId(model.getCustomerId())
                        .customerName(model.getCustomerName())
                        .amount(model.getAmount())
                        .currency(model.getCurrency())
                        .build()
                ).collect(Collectors.toList());
        return accountList;
    }

    /**
     * Write array to Json file
     *
     * @param accountList List of Account
     * @throws IOException throw when file not found
     */
    public void writeJsonToFile(List<Account> accountList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        OutputStream outputStream = new FileOutputStream(ACCOUNT_JSON_FILE);
        writer.writeValue(outputStream, accountList);
    }
}
