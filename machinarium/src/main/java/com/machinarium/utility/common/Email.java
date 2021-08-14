package com.machinarium.utility.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Email {


    /**
     * Creates an {@link Email} from the specified string.
     *
     * @param email The email string to be wrapped.
     * @return The email as an {@link Email} object.
     */
    public static Email of(String email) {return new Email(email);}

    @Override
    public boolean equals(Object otherObject) {

        if(this == otherObject) return true;
        else if (otherObject instanceof Email) {
            return this.email.equals(((Email) otherObject).email);
        }

        return false;
    }

    @Override
    public String toString() {return email;}

    private Email(String email) {this.email = email;}

    private final String email;
}
