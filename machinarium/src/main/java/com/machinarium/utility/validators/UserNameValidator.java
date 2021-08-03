package com.machinarium.utility.validators;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserNameValidator extends MappedValidator {

    public static final String VALID_USER_NAME_CHARACTERS = "a-zA-Z\\d_";

    public static final int MIN_USER_NAME_LENGTH = 4;

    private static final Map<String, String> USER_NAME_VALIDATIONS = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            put(".{" + MIN_USER_NAME_LENGTH + ",}", "Contains at least " + MIN_USER_NAME_LENGTH + " characters.");
            put("^(?!.*[^" + VALID_USER_NAME_CHARACTERS + "]).*", "Doesn't contain invalid characters.");
        }
    });

    public static UserNameValidator getInstance() {
        if(instance == null) instance = new UserNameValidator();
        return instance;
    };

    private UserNameValidator() {
        super(USER_NAME_VALIDATIONS);
    }

    private static UserNameValidator instance;
}
