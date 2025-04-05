package com.example.syy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterBrandActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, waterNameEditText, compositionEditText, sourceEditText;
    Spinner waterTypeSpinner;
    Button registerButton, choosePhotoButton, uploadCertificateButton;

    DatabaseHelper dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_CERTIFICATE_REQUEST = 2;

    private String selectedPhotoPath = "";
    private String selectedCertificatePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_brand);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        waterNameEditText = findViewById(R.id.editTextWaterName);
        compositionEditText = findViewById(R.id.editTextComposition);
        sourceEditText = findViewById(R.id.editTextSource);
        waterTypeSpinner = findViewById(R.id.spinnerWaterType);
        registerButton = findViewById(R.id.buttonRegisterBrand);
        choosePhotoButton = findViewById(R.id.buttonChoosePhoto);
        uploadCertificateButton = findViewById(R.id.buttonUploadCertificate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.water_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(adapter);

        choosePhotoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        uploadCertificateButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_CERTIFICATE_REQUEST);
        });

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String waterName = waterNameEditText.getText().toString();
            String composition = compositionEditText.getText().toString();
            String source = sourceEditText.getText().toString();
            String type = waterTypeSpinner.getSelectedItem().toString();

            if (email.isEmpty() || password.isEmpty() || waterName.isEmpty() || composition.isEmpty()
                    || source.isEmpty() || selectedPhotoPath.isEmpty() || selectedCertificatePath.isEmpty()) {
                Toast.makeText(this, "Барлық өрістерді толтырыңыз", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.addBrand(email, password, waterName, composition, source, type,
                    selectedPhotoPath, selectedCertificatePath);

            if (isInserted) {
                Toast.makeText(this, "Бренд өтінімі жіберілді. Күтудесіз...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Қате пайда болды", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                selectedPhotoPath = data.getData().toString();
                Toast.makeText(this, "Фото выбрано", Toast.LENGTH_SHORT).show();
            } else if (requestCode == PICK_CERTIFICATE_REQUEST) {
                selectedCertificatePath = data.getData().toString();
                Toast.makeText(this, "Сертификат выбран", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
