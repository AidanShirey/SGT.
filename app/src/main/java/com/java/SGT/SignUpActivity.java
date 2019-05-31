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

public class SignUpActivity extends Activity {
    private EditText user;
    private EditText email;
    private EditText pass;
    private EditText confirmpass;
    private ProgressBar wheel;
    private ImageView logo;
    private Button signup;
    String username;
    String eml;
    String password;
    String cpassword;
    boolean infocheck = false;
    FirebaseDatabase database;
    DatabaseReference myRef;
    User ultimateuser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        database = FirebaseDatabase.getInstance();

        // SET UP INTERACTIONS
        wheel = findViewById(R.id.progress);
        logo = findViewById(R.id.sgtlogo);
        user = findViewById(R.id.userentry);
        pass = findViewById(R.id.passentry);
        confirmpass = findViewById(R.id.confirmpassentry);
        email = findViewById(R.id.emailentry);
        signup = findViewById(R.id.signupbutton);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        confirmpass.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        // SET UP LISTENERS

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        confirmpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheel.setVisibility(View.VISIBLE);
                signup.setVisibility(View.INVISIBLE);
                username = user.getText().toString();
                password = pass.getText().toString();
                cpassword = confirmpass.getText().toString();
                eml = email.getText().toString();
                User nuser = new User(username, eml, password);
                //createAccount(username, eml, password);
                ultimateuser = nuser;
                myRef = database.getReference("users");
                infocheck = validateInfo(ultimateuser, cpassword);
                if (infocheck)
                    existCheck(ultimateuser);
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean validateInfo (User users, String conpass){
        // Here we check if the entered information is correct
        // Check if username is in correct format
        String temp = users.usern;
        for (int i = 0; i < temp.length();i++)
        {
            char c = temp.charAt(i);
            if(c == '.' || c == '#' || c == '$' || c == '[' || c == ']') {
                Toast.makeText(SignUpActivity.this, "Username format incorrect: No \'.\', \'#\', \'$\', \'[\', or \']\'.\'", Toast.LENGTH_LONG).show();
                if (wheel.getVisibility() == View.VISIBLE)
                    wheel.setVisibility(View.INVISIBLE);
                if (signup.getVisibility() == View.INVISIBLE)
                    signup.setVisibility(View.VISIBLE);
                return false;
            }
        }
        // Check for valid email requirements
        temp = users.email;
        boolean atsymbol = false;
        boolean period = false;
        for (int i = 0; i < temp.length();i++){
            char c = temp.charAt(i);
            if (c == '@')
                atsymbol = true;
            // only marks period as true once we have seen the @ symbol
            if (c == '.' && atsymbol && !period)
                period = true;
        }
        if (!(atsymbol && period)) {
            Toast.makeText(SignUpActivity.this, "Email format incorrect: Not a valid email.", Toast.LENGTH_SHORT).show();
            if (wheel.getVisibility() == View.VISIBLE)
                wheel.setVisibility(View.INVISIBLE);
            if (signup.getVisibility() == View.INVISIBLE)
                signup.setVisibility(View.VISIBLE);
            return false;
        }
        if(!users.password.equals(conpass)) {
            Toast.makeText(SignUpActivity.this, "Passwords incorrect: passwords do not match.", Toast.LENGTH_SHORT).show();
            if (wheel.getVisibility() == View.VISIBLE)
                wheel.setVisibility(View.INVISIBLE);
            if (signup.getVisibility() == View.INVISIBLE)
                signup.setVisibility(View.VISIBLE);
            return false;
        }
        // No problems with format, return true
        return true;
    }

    public void existCheck(User users) {
        myRef.child(users.usern).child("usern").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    database.getReference().child("users").child(username).setValue(ultimateuser);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("login", false);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Username error: username already exists.", Toast.LENGTH_SHORT).show();
                    if (wheel.getVisibility() == View.VISIBLE)
                        wheel.setVisibility(View.INVISIBLE);
                    if (signup.getVisibility() == View.INVISIBLE)
                        signup.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

}
