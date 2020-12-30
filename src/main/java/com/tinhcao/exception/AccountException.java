package com.tinhcao.exception;

public class AccountException {

    private String errorMessage;

    public AccountException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
