package com.example.shop;

import com.example.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class PasswordEncoderTest {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void setPasswordEncoderTest(){

        String password = "12345";

        String encodePassword = passwordEncoder.encode(password);
        log.info("encodePassword={}",encodePassword);

        assertAll(
                () -> assertNotEquals(password, encodePassword),
                () -> assertTrue(passwordEncoder.matches(password, encodePassword))
        );

    }
}
