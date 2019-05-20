package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.client.AWSMobileClient;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity {
    private EditText user;
    private EditText email;
    private EditText pass;
    private EditText confirmpass;
    private Button signup;
    String username;
    String eml;
    String password;
    DynamoDBMapper dynamoDBMapper;
    AmazonDynamoDBClient dynamoDBClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        if (dynamoDBClient == null)
            Log.d("YourMainActivity", "DBCLIENT IS NULL");

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();
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
                if (dynamoDBClient == null)
                    Log.d("YourMainActivity", "DBCLIENT IS NULL");
                //createAccount(username, eml, password);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void createAccount(final String _username, final String _email, final String _password) {
        final com.amazonaws.models.nosql.AccountDO accountItem = new com.amazonaws.models.nosql.AccountDO();

        accountItem.setUsername(_username);

        accountItem.setEmail(_email);
        accountItem.setPassword(_password);


        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();

        AttributeValue u = new AttributeValue();
        u.setS(_username);
        AttributeValue e = new AttributeValue();
        e.setS(_email);
        AttributeValue p = new AttributeValue();
        p.setS(_password);
        key.put("username", u);
        key.put("email", e);
        key.put("password", p);

        PutItemRequest putItemRequest = new PutItemRequest("Account", key);
        if (dynamoDBClient != null)
            dynamoDBClient.putItem(putItemRequest);

    }
}
