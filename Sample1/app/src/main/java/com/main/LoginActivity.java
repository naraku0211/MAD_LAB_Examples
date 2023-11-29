package com.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.main.fragments.RegisterFragment;
import com.main.helpers.DBHelper;
import com.main.models.User;

public class LoginActivity extends AppCompatActivity {
    private Button submit, register;
    private TextInputEditText username, password;
    String userName, passWord;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submit = findViewById(R.id.submit);
        register = findViewById(R.id.registerAccount);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        dbHelper = new DBHelper(this);

        submit.setOnClickListener(v -> {
            userName = username.getText().toString();
            passWord = password.getText().toString();

            //Online Login (Firebase login)
            if(isNetworkAvailable()){
                //Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();
            }
            //Offline Login (SQLite Login)
            else{
                User user = dbHelper.offlineLoginUser(this, new User(null, userName, passWord, null, null));
                if(user != null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Incorrect username/password.", Toast.LENGTH_SHORT).show();
                }
            }

            /*
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
            */

        });

        register.setOnClickListener(v -> {
            new RegisterFragment().show(getSupportFragmentManager(), RegisterFragment.TAG);
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
