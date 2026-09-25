package com.mastagohim.bukukenangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Define buttons
        Button btnProfile = findViewById(R.id.btn_profile);
        Button btnInputSiswa = findViewById(R.id.btn_input_siswa);
        Button btnInputGuru = findViewById(R.id.btn_input_guru);
        Button btnListSiswa = findViewById(R.id.btn_list_siswa);
        Button btnListGuru = findViewById(R.id.btn_list_guru);
        Button btnEntries = findViewById(R.id.btn_entries);
        Button btnAddEntry = findViewById(R.id.btn_add_entry);
        Button btnSettings = findViewById(R.id.btn_settings);

        // Set listeners
        btnProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        btnInputSiswa.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StudentFormActivity.class)));
        btnInputGuru.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TeacherFormActivity.class)));
        btnListSiswa.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StudentListActivity.class)));
        btnListGuru.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TeacherListActivity.class)));
        btnEntries.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListActivity.class)));
        btnAddEntry.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEntryActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }
}