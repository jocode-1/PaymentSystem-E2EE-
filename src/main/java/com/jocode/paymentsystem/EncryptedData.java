package com.jocode.paymentsystem;

public class EncryptedData {
    private String encryptedData;
    private String encryptedAESKey;

    public EncryptedData(String encryptedData, String encryptedAESKey) {
        this.encryptedData = encryptedData;
        this.encryptedAESKey = encryptedAESKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public String getEncryptedAESKey() {
        return encryptedAESKey;
    }

    @Override
    public String toString() {
        return "EncryptedData{" +
                "encryptedData='" + encryptedData + '\'' +
                ", encryptedAESKey='" + encryptedAESKey + '\'' +
                '}';
    }
}
