package com.tinhcao.model;

import java.util.Currency;

public class Account {
    private long customerId;
    private String customerName;
    private double amount;
    private Currency currency;

    private Account(Builder builder) {
        customerId = builder.customerId;
        customerName = builder.customerName;
        amount = builder.amount;
        currency = builder.currency;

    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                '}';
    }

    public static class Builder {
        private long customerId;
        private String customerName;
        private double amount;
        private Currency currency;

        public Builder customerId(long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
