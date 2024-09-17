package com.jocode.paymentsystem;

import java.util.UUID;

public class Tokenization {
    // Simple token generation for payment data
    public static String tokenize(String paymentData) {
        // Create a random token (UUID)
        return UUID.randomUUID().toString();
    }
}
