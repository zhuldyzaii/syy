package com.example.syy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AdminActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseFirestore.getInstance();
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> confirmBrand("brandId")); // "brandId" - тексерілетін брендтің идентификаторы
    }

    private void confirmBrand(String brandId) {
        db.collection("Brands").document(brandId)
                .update("status", "confirmed") // статус өзгерту
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AdminActivity.this, "Бренд растады!", Toast.LENGTH_SHORT).show();
                    sendConfirmationSMS(brandId); // SMS жіберу
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminActivity.this, "Қате: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendConfirmationSMS(String brandId) {
        // SMS жіберу логикасы, Firebase PhoneAuth немесе басқа API-ны қолдануға болады
    }
}
