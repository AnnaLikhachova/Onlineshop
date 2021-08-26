package com.likhachova.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoder.class);

    private static  final String STRING_IS_FOR_SALT = "This string is for salt";

    public String hash(String passwordToHash) {
        generateSalt();
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
            logger.error("Cannot hash password", e);
            throw new RuntimeException("Cannot hash password", e);
        }
        return generatedPassword;
    }

    public boolean checkPassword(String daoPassword, String password) {
        String generatedHash = hash(password);
        return daoPassword.equals(generatedHash);
    }

    private void generateSalt() {
        byte[] salt = STRING_IS_FOR_SALT.getBytes();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
        }
        catch(NoSuchAlgorithmException e) {
           logger.error("Cannot update salt algorithm", e);
        }
    }
}
