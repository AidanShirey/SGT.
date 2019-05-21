package com.java.SGT;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String usern;
    public String password;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String pass) {
        this.usern = username;
        this.email = email;
        this.password = pass;
    }

}