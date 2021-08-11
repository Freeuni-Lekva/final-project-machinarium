package com.machinarium.utility.validators;

public interface Validator {

    /**
     * @param input The input {@link String} to be validated.
     * @return True if the input string is valid, false otherwise.
     */
    default boolean validate(String input) {
        return this.on(input) == null;
    }

    /**
     * @param input The input {@link String} to be validated.
     * @return Returns the invalidation reason as a String or null if the input is valid.
     */
    String on(String input);
}
