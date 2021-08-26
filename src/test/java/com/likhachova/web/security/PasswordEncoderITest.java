package com.likhachova.web.security;

import com.likhachova.configuration.PropertiesReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PasswordEncoderITest {

    @Test
    @DisplayName("Hash password")
    public void test_hashPassword() {
        String passwordForHash ="user";
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String actualResult = passwordEncoder.hash(passwordForHash);
        assertEquals("1310cfd4cb4cfd23547f873d74469dd0e8350d63b3e810f11d2d10ecc2",actualResult);
  }

    @Test
    @DisplayName("Check if passwords are equal")
    public void test_checkPassword() {
        String passwordForHash ="user";
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String expectedResult = "1310cfd4cb4cfd23547f873d74469dd0e8350d63b3e810f11d2d10ecc2";
        assertTrue(passwordEncoder.checkPassword(expectedResult,passwordForHash));
  }

    @Test
    @DisplayName("Check if passwords are not equal")
    public void test_c() {
        Assertions.assertThrows(AssertionError.class, () -> {
            String passwordForHash ="admin";
            PasswordEncoder passwordEncoder = new PasswordEncoder();
            String expectedResult = "1310cfd4cb4cfd23547f873d74469dd0e8350d63b3e810f11d2d10ecc2";
            assertTrue(passwordEncoder.checkPassword(expectedResult,passwordForHash));
        });
    }
}
