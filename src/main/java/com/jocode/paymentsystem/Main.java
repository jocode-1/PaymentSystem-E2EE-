package com.jocode.paymentsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    // KeyPair for encryption
    private static KeyPair keyPair;
    private static EncryptedData encryptedData; // Store the encrypted data

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
        frame.setSize(600, 700); // Adjust size to fit new fields
        frame.setLayout(new GridLayout(0, 2)); // 2 columns, auto-adjust rows

        // Set padding for the content
        frame.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding for all sides

        // Create bold font for labels
        Font boldFont = new Font("Arial", Font.BOLD, 12);

        // Create form fields with padding and bold text
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(boldFont);
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(boldFont);
        JTextField emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(boldFont);
        JTextField phoneField = new JTextField(15);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(boldFont);
        JTextField addressField = new JTextField(20);

        JLabel dobLabel = new JLabel("Date of Birth (DD/MM/YYYY):");
        dobLabel.setFont(boldFont);
        JTextField dobField = new JTextField(10);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(boldFont);
        String[] genderOptions = {"Male", "Female", "Other"};
        JComboBox<String> genderField = new JComboBox<>(genderOptions);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setFont(boldFont);
        JTextField countryField = new JTextField(20);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setFont(boldFont);
        JTextField cardNumberField = new JTextField(16);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(boldFont);
        JTextField cvvField = new JTextField(3);

        JLabel expiryLabel = new JLabel("Expiry Date (MM/YY):");
        expiryLabel.setFont(boldFont);
        JTextField expiryField = new JTextField(5);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(boldFont);
        JTextField amountField = new JTextField(10);

        JLabel transactionTypeLabel = new JLabel("Transaction Type:");
        transactionTypeLabel.setFont(boldFont);
        String[] transactionTypes = {"Purchase", "Refund"};
        JComboBox<String> transactionTypeField = new JComboBox<>(transactionTypes);

        // Submit button to process encryption
        JButton submitButton = new JButton("Submit & Encrypt");

        // Add components to the frame (UI layout)
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(addressLabel);
        frame.add(addressField);
        frame.add(dobLabel);
        frame.add(dobField);
        frame.add(genderLabel);
        frame.add(genderField);
        frame.add(countryLabel);
        frame.add(countryField);
        frame.add(cardNumberLabel);
        frame.add(cardNumberField);
        frame.add(cvvLabel);
        frame.add(cvvField);
        frame.add(expiryLabel);
        frame.add(expiryField);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(transactionTypeLabel);
        frame.add(transactionTypeField);
        frame.add(new JLabel()); // Spacer
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
                            phoneField.getText(),
                            addressField.getText(),
                            dobField.getText(),
                            genderField.getSelectedItem().toString(),
                            countryField.getText(),
                            cardNumberField.getText(),
                            cvvField.getText(),
                            expiryField.getText(),
                            Double.parseDouble(amountField.getText()),
                            transactionTypeField.getSelectedItem().toString()
                    );

                    // Encrypt the data
                    encryptedData = ClientEncryption.encrypt(transaction.toString(), keyPair.getPublic());

                    // Save the encrypted data to file
                    saveToFile(encryptedData.toString());

                    // Show success dialog with encrypted data and a "Decrypt" button
                    showEncryptedDataDialog(frame, encryptedData);

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

    // Method to show the encrypted data and add a decrypt button
    private static void showEncryptedDataDialog(JFrame frame, EncryptedData encryptedData) {
        // Create a text area to show the encrypted data
        JTextArea textArea = new JTextArea(String.valueOf(encryptedData));
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding inside the text box

        // Create a button for decryption
        JButton decryptButton = new JButton("Decrypt Data");

        // Panel to hold the text area and the button
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(decryptButton, BorderLayout.SOUTH);

        // Create the dialog to show encrypted data and the decrypt button
        JDialog dialog = new JDialog(frame, "Encrypted Transaction Data", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.add(panel);
        dialog.setLocationRelativeTo(frame);

        // Action listener for the decrypt button
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Decrypt the data using the private key
                    String decryptedData = ClientEncryption.decrypt(encryptedData, keyPair.getPrivate());

                    // Show the decrypted data in a new message box
                    JTextArea decryptedTextArea = new JTextArea(decryptedData);
                    decryptedTextArea.setEditable(false);
                    decryptedTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding inside the text box
                    JOptionPane.showMessageDialog(frame, new JScrollPane(decryptedTextArea), "Decrypted Transaction Data", JOptionPane.INFORMATION_MESSAGE);

                    dialog.dispose(); // Close the dialog after decryption

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        // Show the dialog
        dialog.setVisible(true);
    }
}
