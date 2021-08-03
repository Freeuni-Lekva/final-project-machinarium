package com.machinarium.utility.validators;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MappedValidator implements Validator {

    public MappedValidator(Map<String, String> validationMap) {
        this.validationMap = validationMap;
    }

    @Override
    public String on(String input) {

        for(String regexp : validationMap.keySet()) {
            if(!Pattern.compile(regexp).matcher(input).matches()) return validationMap.get(regexp);
        }
        return null;
    };

    private Map<String, String> validationMap;
}
