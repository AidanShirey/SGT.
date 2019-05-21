package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Random;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;





public class SignUpActivity extends Activity {
    private EditText user;
    private EditText email;
    private EditText pass;
    private EditText confirmpass;
    private Button signup;
    String username;
    String eml;
    String password;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        database = FirebaseDatabase.getInstance();

        // SET UP INTERACTIONS
        user = findViewById(R.id.userentry);
        pass = findViewById(R.id.passentry);
        confirmpass = findViewById(R.id.confirmpassentry);
        email = findViewById(R.id.emailentry);
        signup = findViewById(R.id.signupbutton);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        confirmpass.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        // SET UP BUTTON ON CLICK LISTENER
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                eml = email.getText().toString();
                User nuser = new User(username, eml, password);
                Random rand = new Random();
                Integer random = rand.nextInt(100);
                String userId = random.toString();
                myRef = FirebaseDatabase.getInstance().getReference();
                database.getReference().child("users").child(userId).setValue(nuser);
                //createAccount(username, eml, password);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username", username);

                startActivity(intent);
            }
        });

    }

}
