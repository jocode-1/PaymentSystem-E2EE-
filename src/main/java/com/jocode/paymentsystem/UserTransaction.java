package com.jocode.paymentsystem;

import org.json.JSONObject;

public class UserTransaction {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String gender;
    private String country;
    private String cardNumber;
    private String cvv;
    private String expDate;
    private double transactionAmount;
    private String transactionType;
    private String transactionId;

    // Constructor with all fields
    public UserTransaction(String name, String email, String phone, String address, String dob, String gender, String country,
                           String cardNumber, String cvv, String expDate, double transactionAmount, String transactionType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.country = country;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expDate = expDate;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
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
        json.put("phone", phone);
        json.put("address", address);
        json.put("dob", dob);
        json.put("gender", gender);
        json.put("country", country);
        json.put("cardNumber", cardNumber);
        json.put("cvv", cvv);
        json.put("expDate", expDate);
        json.put("transactionAmount", transactionAmount);
        json.put("transactionType", transactionType);
        json.put("transactionId", transactionId);
        return json.toString(4); // Pretty print with indentation
    }

    @Override
    public String toString() {
        return toJson();
    }
}
