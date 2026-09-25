package com.mastagohim.bukukenangan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.InputStream;

public class StudentFormActivity extends AppCompatActivity {

    private EditText editName, editAddress, editPhone, editNim;
    private ImageView imgProfile;
    private Button btnUpload, btnSave;
    private Spinner spMajor, spClass;
    private Bitmap profileBitmap;
    private Uri selectedImageUri;

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
                        Toast.makeText(StudentFormActivity.this, "Gagal memuat foto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        editName = findViewById(R.id.edit_name);
        editNim = findViewById(R.id.edit_nim);
        editAddress = findViewById(R.id.edit_address);
        editPhone = findViewById(R.id.edit_phone);
        imgProfile = findViewById(R.id.img_profile);
        btnUpload = findViewById(R.id.btn_upload_photo);
        btnSave = findViewById(R.id.btn_save_student);
        spMajor = findViewById(R.id.sp_major);
        spClass = findViewById(R.id.sp_class);

        populateSpinners();
        setupClickListeners();
    }

    private void populateSpinners() {
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(
                this, R.array.major_options, android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMajor.setAdapter(majorAdapter);

        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(
                this, R.array.class_options, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClass.setAdapter(classAdapter);
    }

    private void setupClickListeners() {
        btnUpload.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        btnSave.setOnClickListener(v -> saveStudentData());
    }

    private void saveStudentData() {
        String name = editName.getText() != null ? editName.getText().toString().trim() : "";
        String nim = editNim.getText() != null ? editNim.getText().toString().trim() : "";
        String address = editAddress.getText() != null ? editAddress.getText().toString().trim() : "";
        String phone = editPhone.getText() != null ? editPhone.getText().toString().trim() : "";
        String major = spMajor.getItemAtPosition(spMajor.getSelectedItemPosition()).toString();
        String cls = spClass.getItemAtPosition(spClass.getSelectedItemPosition()).toString();
        String photoUri = selectedImageUri != null ? selectedImageUri.toString() : "";

        if (name.isEmpty()) {
            Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(name, nim, major, cls, address, phone);
        student.setPhotoUri(photoUri);

        long id = dbHelper.saveStudent(student);
        if (id != -1) {
            Toast.makeText(this, "Data siswa disimpan!\nID: " + id, Toast.LENGTH_LONG).show();
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