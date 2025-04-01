package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kursovoi.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button btnLogin, btnRegister;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            if (sessionManager.getUserRole().equals("admin")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (userName.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Логин және құпиясөзді енгізіңіз!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPassword.equals("1234")) {
                    Toast.makeText(LoginActivity.this, "Админ ретінде кірдіңіз!", Toast.LENGTH_SHORT).show();
                    sessionManager.setLogin(true, "admin");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else if (databaseHelper.checkUser(userName, userPassword)) {
                    Toast.makeText(LoginActivity.this, "Қолданушы ретінде кірдіңіз!", Toast.LENGTH_SHORT).show();
                    sessionManager.setLogin(true, "user");
                    startActivity(new Intent(LoginActivity.this, UserActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Қате логин немесе пароль!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
