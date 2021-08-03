package com.machinarium.utility.validators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @BeforeEach
    void setUp() {
        validator = PasswordValidator.getInstance();
    }

    @Test
    void on() {

        // Some positive cases.
        assertNull(validator.on("SomePass*1"));
        assertNull(validator.on("Anothervalidpass@1"));
        assertNull(validator.on("this_Val1"));
        assertNull(validator.on("too_Val1"));

        // Negative cases.
        String REJECT_STRING;

        // "Contains one lower case letter.":
        REJECT_STRING = "Contains one lower case letter.";
        assertEquals(REJECT_STRING, validator.on("ADA12132_"));
        assertEquals(REJECT_STRING, validator.on("MADEUP_PASS1"));
        assertEquals(REJECT_STRING, validator.on("ANUDAUNA2???"));

        // "Contains one upper case letter.":
        REJECT_STRING = "Contains one upper case letter.";
        assertEquals(REJECT_STRING, validator.on("ada12132_"));
        assertEquals(REJECT_STRING, validator.on("madeup_pass1"));
        assertEquals(REJECT_STRING, validator.on("anudauna2??<"));

        // "Contains one digit.":
        REJECT_STRING = "Contains one digit.";
        assertEquals(REJECT_STRING, validator.on("adaADWAD_"));
        assertEquals(REJECT_STRING, validator.on("madeup_passADWAWD"));
        assertEquals(REJECT_STRING, validator.on("anudaunaDD??<"));

        // "Contains one special character from: !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~.":
        REJECT_STRING = "Contains one special character from: !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~.";
        assertEquals(REJECT_STRING, validator.on("adaADW12"));
        assertEquals(REJECT_STRING, validator.on("madeuppa121"));
        assertEquals(REJECT_STRING, validator.on("a2nudaunaDD"));


        // "Contains at least " + PasswordValidator.MIN_PASSWORD_LENGTH + " characters.":
        REJECT_STRING = "Contains at least " + PasswordValidator.MIN_PASSWORD_LENGTH + " characters.";
        assertEquals(REJECT_STRING, validator.on("Aa1?"));
        assertEquals(REJECT_STRING, validator.on("BBBB1?a"));
        assertEquals(REJECT_STRING, validator.on("DW1?a"));

        // "Doesn't contain invalid characters.":
        REJECT_STRING = "Doesn't contain invalid characters.";
        assertEquals(REJECT_STRING, validator.on("\nawd123A_"));
        assertEquals(REJECT_STRING, validator.on("adb1AAA_\n"));
        assertEquals(REJECT_STRING, validator.on("adb1AAA\n"));
    }

    @Test
    void validate() {

        // Some positive cases.
        assertTrue(validator.validate("SomePass_1"));
        assertTrue(validator.validate("Anothervalidpass@1"));
        assertTrue(validator.validate("this_Val1"));
        assertTrue(validator.validate("too_Val1"));

        // Negative cases.
        // "Contains one lower case letter.":
        assertFalse(validator.validate("ADA12132_"));
        assertFalse(validator.validate("MADEUP_PASS1"));
        assertFalse(validator.validate("ANUDAUNA2???"));

        // "Contains one upper case letter.":
        assertFalse(validator.validate("ada12132_"));
        assertFalse(validator.validate("madeup_pass1"));
        assertFalse(validator.validate("anudauna2??<"));

        // "Contains one digit.":
        assertFalse(validator.validate("adaADWAD_"));
        assertFalse(validator.validate("madeup_passADWAWD"));
        assertFalse(validator.validate("anudaunaDD??<"));

        // "Contains one special character from: \"" + PasswordValidator.SPECIAL_CHARACTERS.replace("\\", "") + "\\\".":
        assertFalse(validator.validate("adaADW12"));
        assertFalse(validator.validate("madeuppa121"));
        assertFalse(validator.validate("a2nudaunaDD"));

        // "Contains at least " + PasswordValidator.MIN_PASSWORD_LENGTH + " characters.":
        assertFalse(validator.validate("Aa1?"));
        assertFalse(validator.validate("BBBB1?a"));
        assertFalse(validator.validate("DW1?a"));

        // "Doesn't contain invalid characters.":
        assertFalse(validator.validate("\nawd123A_"));
        assertFalse(validator.validate("adb1AAA_\n"));
        assertFalse(validator.validate("adb1AAA\n"));
    }

    private PasswordValidator validator;
}