package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private String password;
    private boolean infocheck;
    private User ultimateuser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Quiz uquiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupbutton);
        username = findViewById(R.id.emailentry);
        pass = findViewById(R.id.passentry);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        database = FirebaseDatabase.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public boolean validateInfo(User users)
    {
        // Here we check if the entered information is correct
        // Check if username is in correct format
        String temp = users.usern;
        for (int i = 0; i < temp.length();i++)
        {
            char c = temp.charAt(i);
            if(c == '.' || c == '#' || c == '$' || c == '[' || c == ']') {
                Toast.makeText(LoginActivity.this, "Username format incorrect: No \'.\', \'#\', \'$\', \'[\', or \']\'.\'", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

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
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login failed. Password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (!dataSnapshot.exists()) {
                    Toast.makeText(LoginActivity.this, "Login failed. User does not exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



    }
}
