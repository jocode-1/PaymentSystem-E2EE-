package com.jocode.paymentsystem;

import java.security.PrivateKey;
import javax.crypto.Cipher;
import java.util.Base64;

public class PaymentGateway {
    private static final String ALGORITHM = "RSA";

    // Decrypt data using the private key
    public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes); // Convert decrypted bytes to string
    }
}
