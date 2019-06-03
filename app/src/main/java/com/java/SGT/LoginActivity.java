package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends Activity {
    private Button login;
    private Button signup;
    private EditText username;
    private EditText pass;
    private String usern;
    private ImageView logo;
    private String password;
    private boolean infocheck;
    private User ultimateuser;
    private ProgressBar wheel;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Find all views that may need to be modified
        wheel = findViewById(R.id.progress);
        logo = findViewById(R.id.sgtlogo);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupbutton);
        username = findViewById(R.id.userentry);
        pass = findViewById(R.id.passentry);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        // Set up database connection for queries
        database = FirebaseDatabase.getInstance();

        // Check if the loading wheel is visible
        if (wheel.getVisibility() == View.VISIBLE)
            wheel.setVisibility(View.INVISIBLE);
        if (login.getVisibility() == View.INVISIBLE)
            login.setVisibility(View.VISIBLE);
        if (signup.getVisibility() == View.INVISIBLE)
            signup.setVisibility(View.VISIBLE);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheel.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.INVISIBLE);
                usern = username.getText().toString();
                password = pass.getText().toString();
                User nuser = new User(username.getText().toString(), "", pass.getText().toString());
                ultimateuser = nuser;
                myRef = database.getReference("users");
                infocheck = validateInfo(ultimateuser);
                if (infocheck)
                    existCheck(ultimateuser);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    // A function to hide the keyboard when the user
    // taps on blank space.
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // A function where we determine if the entered information
    // is correct, meaning that the information entered will be
    // able to be queried.
    public boolean validateInfo(User users)
    {
        // Check if username is in correct format
        String temp = users.usern;
        for (int i = 0; i < temp.length();i++)
        {
            char c = temp.charAt(i);
            if(c == '.' || c == '#' || c == '$' || c == '[' || c == ']') {
                Toast.makeText(LoginActivity.this, "Username format incorrect: No \'.\', \'#\', \'$\', \'[\', or \']\'.\'", Toast.LENGTH_LONG).show();
                if (wheel.getVisibility() == View.VISIBLE)
                    wheel.setVisibility(View.INVISIBLE);
                if (login.getVisibility() == View.INVISIBLE)
                    login.setVisibility(View.VISIBLE);
                if (signup.getVisibility() == View.INVISIBLE)
                    signup.setVisibility(View.VISIBLE);
                return false;
            }
        }
        return true;
    }


    // A function where we check if the users entered information
    // already exists within the database, so we can retrieve the
    // information to load within the home activity.
    public void existCheck(User users){
        myRef.child(users.usern).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String temp = dataSnapshot.getValue(String.class);
                    if (temp.equals(password)) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username", usern);
                        intent.putExtra("login", true);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login failed. Password incorrect.", Toast.LENGTH_SHORT).show();
                        if (wheel.getVisibility() == View.VISIBLE)
                            wheel.setVisibility(View.INVISIBLE);
                        if (login.getVisibility() == View.INVISIBLE)
                            login.setVisibility(View.VISIBLE);
                        if (signup.getVisibility() == View.INVISIBLE)
                            signup.setVisibility(View.VISIBLE);
                    }
                }
                else if (!dataSnapshot.exists()) {
                    Toast.makeText(LoginActivity.this, "Login failed. User does not exist.", Toast.LENGTH_SHORT).show();
                    if (wheel.getVisibility() == View.VISIBLE)
                        wheel.setVisibility(View.INVISIBLE);
                    if (login.getVisibility() == View.INVISIBLE)
                        login.setVisibility(View.VISIBLE);
                    if (signup.getVisibility() == View.INVISIBLE)
                        signup.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



    }
}
