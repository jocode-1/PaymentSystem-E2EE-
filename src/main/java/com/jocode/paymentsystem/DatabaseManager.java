package com.jocode.paymentsystem;


import java.security.PrivateKey;
import java.sql.*;

public class DatabaseManager {

    public static void saveEncryptedData(String fullName, String email, String encryptedData, String encryptedAESKey) {
        String query = "INSERT INTO encrypted_info (full_name, email, encrypted_data, encrypted_aes_key) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getUsername(), DatabaseConfig.getPassword());
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, encryptedData);
            pstmt.setString(4, encryptedAESKey);
            pstmt.executeUpdate();
            System.out.println("Encrypted data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveDecryptedData(PrivateKey privateKey) {
        String query = "SELECT full_name, email, encrypted_data, encrypted_aes_key FROM encrypted_info";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getUsername(), DatabaseConfig.getPassword());
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String encryptedData = rs.getString("encrypted_data");
                String encryptedAESKey = rs.getString("encrypted_aes_key");

                // Decrypt the AES key
                EncryptedData encryptedRecord = new EncryptedData(encryptedData, encryptedAESKey);
                String decryptedData = ClientEncryption.decrypt(encryptedRecord, privateKey);

                System.out.println("Decrypted Info:");
                System.out.println("Full Name: " + fullName);
                System.out.println("Email: " + email);
                System.out.println("Decrypted Data: " + decryptedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
