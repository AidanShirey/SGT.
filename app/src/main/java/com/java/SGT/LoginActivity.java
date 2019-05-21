package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    private Button login;
    private Button signup;
    private EditText username;
    private EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupbutton);
        username = findViewById(R.id.emailentry);
        pass = findViewById(R.id.passentry);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("login", true);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
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
}
