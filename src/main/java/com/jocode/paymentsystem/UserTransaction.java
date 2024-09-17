package com.jocode.paymentsystem;

import org.json.JSONObject;

public class UserTransaction {
    private String name;
    private String email;
    private String address;
    private String cardNumber;
    private String cvv;
    private String expDate;
    private double transactionAmount;
    private String transactionId;

    public UserTransaction(String name, String email, String address, String cardNumber, String cvv, String expDate, double transactionAmount) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expDate = expDate;
        this.transactionAmount = transactionAmount;
        this.transactionId = generateTransactionId();
    }

    // Generate a unique transaction ID
    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }

    // Convert the transaction data to JSON format
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        json.put("address", address);
        json.put("cardNumber", cardNumber);
        json.put("cvv", cvv);
        json.put("expDate", expDate);
        json.put("transactionAmount", transactionAmount);
        json.put("transactionId", transactionId);
        return json.toString(4); // Pretty print with indentation
    }

    @Override
    public String toString() {
        return toJson();
    }
}
