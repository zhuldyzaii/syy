package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.syy.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btnLogin;
    private TextView txtRegister;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Элементтерді табу
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        // Модульдер
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Логин батырмасының басылуы
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Өрістердің бос емес екендігін тексеру
                if (userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Барлық өрісті толтырыңыз!", Toast.LENGTH_SHORT).show();
                }
                // Email форматының дұрыстығын тексеру
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "Қате email форматы!", Toast.LENGTH_SHORT).show();
                }
                // Егер бәрі дұрыс болса, қолданушыны тексеру
                else {
                    boolean isValid = databaseHelper.checkUser(userEmail, userPassword);
                    if (isValid) {
                        // Сессияны бастау
                        sessionManager.setLogin(true, "user");
                        Toast.makeText(LoginActivity.this, "Кіру сәтті!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Қате болса
                        Toast.makeText(LoginActivity.this, "Қате! Email немесе құпиясөз дұрыс емес.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Тіркелу экранына көшу
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
