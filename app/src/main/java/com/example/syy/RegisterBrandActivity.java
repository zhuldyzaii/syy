package com.example.syy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syy.model.Brand;
import com.example.syy.utils.CloudinaryUploader;
import com.example.syy.utils.CloudinaryUploader.UploadResultCallback;  // Импортируйте правильный интерфейс
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterBrandActivity extends AppCompatActivity {

    private EditText brandNameEditText;
    private Button registerBrandButton, uploadCertificateButton;
    private Uri certificateUri;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private final ActivityResultLauncher<Intent> certificatePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    certificateUri = result.getData().getData();
                    Toast.makeText(this, "Сертификат таңдалды!", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_brand);

        brandNameEditText = findViewById(R.id.brandNameEditText);
        registerBrandButton = findViewById(R.id.registerBrandButton);
        uploadCertificateButton = findViewById(R.id.uploadCertificateButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        uploadCertificateButton.setOnClickListener(v -> selectCertificate());
        registerBrandButton.setOnClickListener(v -> registerBrand());
    }

    private void selectCertificate() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        certificatePickerLauncher.launch(intent);
    }

    private void registerBrand() {
        String brandName = brandNameEditText.getText().toString().trim();

        if (brandName.isEmpty() || certificateUri == null) {
            Toast.makeText(this, "Барлық мәліметтерді толтырыңыз!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();

        CloudinaryUploader.uploadFileToCloudinary(getApplicationContext(), certificateUri, "certificate", new UploadResultCallback() {
            @Override
            public void onSuccess(String url) {
                Brand brand = new Brand(brandName, "pending", url);
                db.collection("Brands").document(userId)
                        .set(brand)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(RegisterBrandActivity.this, "Бренд тіркелді. Админ тексеретін болады.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(RegisterBrandActivity.this, "Қате: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(RegisterBrandActivity.this, "Сертификатты жүктеу қатесі: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
