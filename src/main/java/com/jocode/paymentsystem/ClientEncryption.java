package com.jocode.paymentsystem;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Base64;

public class ClientEncryption {
    // Generate RSA public/private key pair
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Encrypt large data using AES and encrypt AES key with RSA
    public static EncryptedData encrypt(String data, PublicKey publicKey) throws Exception {
        // Step 1: Generate AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256 key size
        SecretKey aesKey = keyGen.generateKey();

        // Step 2: Encrypt data using AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedData = aesCipher.doFinal(data.getBytes());

        // Step 3: Encrypt AES key using RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAESKey = rsaCipher.doFinal(aesKey.getEncoded());

        // Encode encrypted data and AES key as Base64 for readability
        return new EncryptedData(Base64.getEncoder().encodeToString(encryptedData), Base64.getEncoder().encodeToString(encryptedAESKey));
    }

    // Decrypt AES-encrypted data using RSA-encrypted AES key
    public static String decrypt(EncryptedData encryptedData, PrivateKey privateKey) throws Exception {
        // Step 1: Decrypt AES key using RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] aesKeyBytes = rsaCipher.doFinal(Base64.getDecoder().decode(encryptedData.getEncryptedAESKey()));

        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, 0, aesKeyBytes.length, "AES");

        // Step 2: Decrypt data using AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] decryptedData = aesCipher.doFinal(Base64.getDecoder().decode(encryptedData.getEncryptedData()));

        return new String(decryptedData);
    }

}
