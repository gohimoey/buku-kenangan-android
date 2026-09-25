package com.mastagohim.bukukenangan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MajorClassActivity extends AppCompatActivity {
    private String majorCode, majorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        majorCode = getIntent().getStringExtra("major_code");
        majorName = getIntent().getStringExtra("major_name");

        setupButtons();
    }

    private void setupButtons() {
        String prefix = majorCode != null ? majorCode : "TKJ";

        // TKJ Buttons
        findViewById(R.id.btn_tkj_11).setOnClickListener(v -> openClass("TKJ", "11"));
        findViewById(R.id.btn_tkj_12).setOnClickListener(v -> openClass("TKJ", "12"));
        findViewById(R.id.btn_tkj_13).setOnClickListener(v -> openClass("TKJ", "13"));

        // TKR Buttons
        findViewById(R.id.btn_tkr_11).setOnClickListener(v -> openClass("TKR", "11"));
        findViewById(R.id.btn_tkr_12).setOnClickListener(v -> openClass("TKR", "12"));
        findViewById(R.id.btn_tkr_13).setOnClickListener(v -> openClass("TKR", "13"));

        // PBS Buttons
        findViewById(R.id.btn_pbs_11).setOnClickListener(v -> openClass("PBS", "11"));
        findViewById(R.id.btn_pbs_12).setOnClickListener(v -> openClass("PBS", "12"));
        findViewById(R.id.btn_pbs_13).setOnClickListener(v -> openClass("PBS", "13"));
    }

    private void openClass(String major, String className) {
        String selection = major + " - Kelas " + className;
        Toast.makeText(this, "Pilih: " + selection, Toast.LENGTH_SHORT).show();
        // You can open a specific activity or show a list here
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}