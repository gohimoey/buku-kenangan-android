package com.mastagohim.bukukenangan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.InputStream;

public class TeacherFormActivity extends AppCompatActivity {

    private EditText editName, editNip, editSubject, editAddress, editPhone;
    private ImageView imgProfile;
    private Button btnUpload, btnSave;
    private Uri selectedImageUri;
    private Bitmap profileBitmap;

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        if (inputStream != null) {
                            profileBitmap = BitmapFactory.decodeStream(inputStream);
                            imgProfile.setImageBitmap(profileBitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(TeacherFormActivity.this, "Gagal memuat foto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        editName = findViewById(R.id.edit_name);
        editNip = findViewById(R.id.edit_nip);
        editSubject = findViewById(R.id.edit_subject);
        editAddress = findViewById(R.id.edit_address);
        editPhone = findViewById(R.id.edit_phone);
        imgProfile = findViewById(R.id.img_profile);
        btnUpload = findViewById(R.id.btn_upload_photo);
        btnSave = findViewById(R.id.btn_save_teacher);

        btnUpload.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        btnSave.setOnClickListener(v -> saveTeacherData());
    }

    private void saveTeacherData() {
        String name = editName.getText() != null ? editName.getText().toString().trim() : "";
        String nip = editNip.getText() != null ? editNip.getText().toString().trim() : "";
        String subject = editSubject.getText() != null ? editSubject.getText().toString().trim() : "";
        String address = editAddress.getText() != null ? editAddress.getText().toString().trim() : "";
        String phone = editPhone.getText() != null ? editPhone.getText().toString().trim() : "";
        String photoUri = selectedImageUri != null ? selectedImageUri.toString() : "";

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama guru wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Teacher teacher = new Teacher(name, nip, subject, address, phone);
        teacher.setPhotoUri(photoUri);

        long id = dbHelper.saveTeacher(teacher);
        if (id != -1) {
            Toast.makeText(this, "Data guru disimpan!\nID: " + id, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}