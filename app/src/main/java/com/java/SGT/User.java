package com.java.SGT;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String usern;
    public String password;
    public String email;
    public Boolean q1;
    public Boolean q2;
    public Boolean q3;
    public Boolean q4;
    public Boolean q5;
    public Boolean q6;
    public Boolean q7;
    public Boolean q8;
    public Boolean q9;
    public Boolean q10;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String pass) {
        this.usern = username;
        this.email = email;
        this.password = pass;
        q1 = q2 = q3 = q4 = q5 = q6 = q7 = q8 = q9 = q10 = false;
    }

}