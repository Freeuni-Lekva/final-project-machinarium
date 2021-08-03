package com.machinarium.utility.validators;

import com.machinarium.utility.validators.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PasswordValidator extends MappedValidator {

    public static final int MIN_PASSWORD_LENGTH = 8;

    public static final String VALID_PASSWORD_CHARACTERS = "a-zA-Z\\d\\p{Punct}";

    private static final Map<String, String> PASSWORD_VALIDATIONS = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            put(".*[a-z].*", "Contains one lower case letter.");
            put(".*[A-Z].*", "Contains one upper case letter.");
            put(".*[\\d].*", "Contains one digit.");
            put(".*[\\p{Punct}].*", "Contains one special character from: !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~.");
            put(".{" + MIN_PASSWORD_LENGTH + ",}", "Contains at least " + MIN_PASSWORD_LENGTH + " characters.");
            put("^(?!.*[^" + VALID_PASSWORD_CHARACTERS + "]).*", "Doesn't contain invalid characters.");
        }
    });

    public static PasswordValidator getInstance() {
        if(instance == null) instance = new PasswordValidator();
        return instance;
    };

    private PasswordValidator() {
        super(PASSWORD_VALIDATIONS);
    }

    private static PasswordValidator instance;
}
