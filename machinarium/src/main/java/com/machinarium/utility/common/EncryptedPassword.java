package com.machinarium.utility.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class EncryptedPassword {

    private static final String ENCRYPTION = "SHA-256";

    /**
     * Creates an encrypted password from the specified string.
     *
     * @param password The initial {@link String} to be encrypted.
     * @return The encrypted string as an {@link EncryptedPassword} object.
     */
    public static EncryptedPassword of(String password) {

        EncryptedPassword encryptedPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION);

            messageDigest.reset();
            messageDigest.update(password.getBytes());

            encryptedPassword = new EncryptedPassword(new String(Hex.encodeHex(messageDigest.digest())));

        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}

        return encryptedPassword;
    }

    /**
     * Wraps the specified string into an {@link EncryptedPassword} object.
     *
     * @param encryptedPassword The encrypted password to be wrapped.
     * @return An {@link EncryptedPassword} object containing the same string as the one passed.
     */
    public static EncryptedPassword from(String encryptedPassword) {
        return new EncryptedPassword(encryptedPassword);
    }

    @Override
    public boolean equals(Object otherObject) {

        if(this == otherObject) return true;
        else if (otherObject instanceof EncryptedPassword) {
            return this.encryptedPassword.equals(((EncryptedPassword) otherObject).encryptedPassword);
        }

        return false;
    }

    @Override
    public String toString() {return encryptedPassword;}

    private EncryptedPassword(String encryptedPassword) {this.encryptedPassword = encryptedPassword;}

    private final String encryptedPassword;
}
