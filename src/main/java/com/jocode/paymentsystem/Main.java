package com.jocode.paymentsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    // KeyPair for encryption
    private static KeyPair keyPair;

    public static void main(String[] args) {
        try {
            // Generate RSA key pair
            keyPair = ClientEncryption.generateKeyPair();

            // Create the UI
            createUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UI creation for user input
    private static void createUI() {
        // Create the main window (JFrame)
        JFrame frame = new JFrame("User Transaction Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
//        frame.
        frame.setLayout(new GridLayout(0, 2)); // 2 columns, auto-adjust rows

        // Create form fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        JTextField cardNumberField = new JTextField(16);

        JLabel cvvLabel = new JLabel("CVV:");
        JTextField cvvField = new JTextField(3);

        JLabel expiryLabel = new JLabel("Expiry Date (MM/YY):");
        JTextField expiryField = new JTextField(5);

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);

        // Submit button to process encryption
        JButton submitButton = new JButton("Submit & Encrypt");

        // Add components to the frame (UI layout)
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(addressLabel);
        frame.add(addressField);
        frame.add(cardNumberLabel);
        frame.add(cardNumberField);
        frame.add(cvvLabel);
        frame.add(cvvField);
        frame.add(expiryLabel);
        frame.add(expiryField);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Collect input data
                    UserTransaction transaction = new UserTransaction(
                            nameField.getText(),
                            emailField.getText(),
                            addressField.getText(),
                            cardNumberField.getText(),
                            cvvField.getText(),
                            expiryField.getText(),
                            Double.parseDouble(amountField.getText())
                    );

                    // Encrypt the data
                    EncryptedData encryptedData = ClientEncryption.encrypt(transaction.toString(), keyPair.getPublic());

                    // Save the encrypted data to file
                    saveToFile(encryptedData.toString());

                    // Show success dialog
                    JOptionPane.showMessageDialog(frame, "Transaction encrypted and saved successfully!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Helper method to save the result to a file with a date-based filename
    private static void saveToFile(String encryptedData) throws Exception {
        // Create the file name based on the current timestamp
        String dateString = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = "Transaction_" + dateString + ".txt";

        // Write to the file
        java.nio.file.Path filePath = java.nio.file.Paths.get(fileName);
        java.nio.file.Files.write(filePath, encryptedData.getBytes());

        System.out.println("Encrypted data saved to: " + filePath.toAbsolutePath());
    }
}
