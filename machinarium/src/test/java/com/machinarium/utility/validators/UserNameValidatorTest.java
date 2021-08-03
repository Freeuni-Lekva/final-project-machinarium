package com.machinarium.utility.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameValidatorTest {

    @BeforeEach
    void setUp() {
        validator = UserNameValidator.getInstance();
    }

    @Test
    void on() {

        // Some positive cases.
        assertNull(validator.on("UserName"));
        assertNull(validator.on("MadeupUsername"));
        assertNull(validator.on("this_is_still_valid"));
        assertNull(validator.on("this"));

        // Negative cases.
        assertEquals("Contains at least " + UserNameValidator.MIN_USER_NAME_LENGTH + " characters.", validator.on("no"));
        assertEquals("Contains at least " + UserNameValidator.MIN_USER_NAME_LENGTH + " characters.", validator.on("nop"));
        assertEquals("Doesn't contain invalid characters.", validator.on("lan inv cha"));
        assertEquals("Doesn't contain invalid characters.", validator.on("1??1"));
    }

    @Test
    void validate() {

        // Some positive cases.
        assertTrue(validator.validate("UserName"));
        assertTrue(validator.validate("MadeupUsername"));
        assertTrue(validator.validate("this_is_still_valid"));
        assertTrue(validator.validate("this"));

        // Negative cases.
        assertFalse(validator.validate("no"));
        assertFalse(validator.validate("nop"));
        assertFalse(validator.validate("lan invalid character"));
        assertFalse(validator.validate("1??1"));
    }

    private UserNameValidator validator;
}