package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterUserActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button registerButton;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Барлық өрістерді толтырыңыз", Toast.LENGTH_SHORT).show();
                return;
            }

            // Қарапайым қолданушыны тіркеу
            boolean isInserted = dbHelper.addUser(email, password);

            if (isInserted) {
                Toast.makeText(this, "Тіркеу сәтті аяқталды", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Бұл email бұрын тіркелген болуы мүмкін", Toast.LENGTH_SHORT).show();
            }
        });
    }
}









