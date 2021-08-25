package com.likhachova.web.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {


    private final String saltString = "This string is for salt";

    public PasswordEncoder() {
        generateSalt();
    }

    public String hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString(bytes[i] & 0xff, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch(NoSuchAlgorithmException e) {
            //LOGGER.error("Cannot hash password", e);
        }
        return generatedPassword;
    }

    public boolean checkPassword(String daoPassword, String password) {
        String generatedHash = hash(password);
        return daoPassword.equals(generatedHash);
    }

    private void generateSalt() {
        byte[] salt = saltString.getBytes();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
        }
        catch(NoSuchAlgorithmException e) {
           // LOGGER.error("Cannot generate salt", e);
        }
    }
}
