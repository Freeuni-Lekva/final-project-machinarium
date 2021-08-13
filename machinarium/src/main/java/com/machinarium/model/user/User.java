package com.machinarium.model.user;

import com.machinarium.utility.EncryptedPassword;

import java.util.Objects;

public class User {

    public User(String userName, EncryptedPassword password, String email) {

        Objects.requireNonNull(this.userName = userName, "The user name cannot be null.");
        Objects.requireNonNull(this.password = password, "The password for a user cannot be null.");
        this.email = email;
    }

    public String getUserName() {return userName;}

    public EncryptedPassword getPassword() {return password;}

    public String getEmail() {return this.email;}

    @Override
    public boolean equals(Object otherObject) {

        if (this == otherObject) return true;
        else if (otherObject instanceof User) {
            return this.userName.equals(((User) otherObject).userName);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    private final String userName;
    private final EncryptedPassword password;
    private final String email;

}
