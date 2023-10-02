package com.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private Button submit, register;
    private TextInputEditText username, password;
    String userName, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submit = findViewById(R.id.submit);
        register = findViewById(R.id.registerAccount);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        submit.setOnClickListener(v -> {
            userName = username.getText().toString();
            passWord = password.getText().toString();

            if(userName.equals("admin") && passWord.equals("password")){
                //Login Successful. Proceed to MainActivity
                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else{
                //Display incorrect credentials.
                Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(v -> {

        });
    }
}
