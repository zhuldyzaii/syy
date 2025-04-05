package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button userButton, brandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userButton = findViewById(R.id.btn_user);
        brandButton = findViewById(R.id.btn_brand);

        userButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterUserActivity.class);
            startActivity(intent);
        });

        brandButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterBrandActivity.class);
            startActivity(intent);
        });
    }
}
