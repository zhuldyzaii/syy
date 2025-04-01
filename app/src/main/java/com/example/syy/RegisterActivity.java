package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, password, confirmPassword;
    private Button btnRegister;
    private TextView txtLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Жаңа өрістерді қайта анықтау
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();

                // Тексеру логикасы
                if (userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Барлық өрісті толтырыңыз!", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Қате email форматы!", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(userConfirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Құпиясөздер сәйкес келмейді!", Toast.LENGTH_SHORT).show();
                } else if (userPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Құпиясөз кемінде 6 символдан тұруы керек!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = databaseHelper.addUser(userEmail, userPassword);
                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Тіркелу сәтті аяқталды!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Қате! Бұл email тіркелген болуы мүмкін.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
