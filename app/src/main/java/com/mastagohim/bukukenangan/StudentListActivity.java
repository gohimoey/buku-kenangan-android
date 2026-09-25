package com.mastagohim.bukukenangan;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private DatabaseHelper dbHelper;

    private Spinner spFilterMajor, spFilterClass;

    private List<String> fullMajorOptions;
    private List<String> fullClassOptions;

    private static final int REQUEST_WRITE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_students);
        spFilterMajor = findViewById(R.id.sp_filter_major);
        spFilterClass = findViewById(R.id.sp_filter_class);

        setupSpinners();
        adapter = new StudentAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadStudents();
        setupSpinnerListeners();
    }

    private void setupSpinners() {
        fullMajorOptions = new ArrayList<>();
        fullMajorOptions.add("Semua Jurusan");
        fullMajorOptions.addAll(Arrays.asList(getResources().getStringArray(R.array.major_options)));

        fullClassOptions = new ArrayList<>();
        fullClassOptions.add("Semua Kelas");
        fullClassOptions.addAll(Arrays.asList(getResources().getStringArray(R.array.class_options)));

        ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fullMajorOptions);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterMajor.setAdapter(majorAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fullClassOptions);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterClass.setAdapter(classAdapter);
    }

    private void setupSpinnerListeners() {
        spFilterMajor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndReload();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
        spFilterClass.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndReload();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void filterAndReload() {
        String selectedMajor = spFilterMajor.getSelectedItem().toString();
        String selectedClass = spFilterClass.getSelectedItem().toString();

        List<Student> allStudents = dbHelper.getAllStudents();
        List<Student> filtered = new ArrayList<>();

        for (Student s : allStudents) {
            boolean majorMatch = selectedMajor.equals("Semua Jurusan") ||
                                 (s.getMajor() != null && s.getMajor().contains(selectedMajor));
            boolean classMatch = selectedClass.equals("Semua Kelas") ||
                                 (s.getClassName() != null && s.getClassName().contains(selectedClass));
            if (majorMatch && classMatch) {
                filtered.add(s);
            }
        }
        adapter.updateData(filtered);
    }

    private void loadStudents() {
        List<Student> students = dbHelper.getAllStudents();
        adapter.updateData(students);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_export) {
            exportCsv();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportCsv() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
            return;
        }
        exportToCsv();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                              @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            exportToCsv();
        } else {
            Toast.makeText(this, "Izin penulisan diperlukan untuk export", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportToCsv() {
        List<Student> students = dbHelper.getAllStudents();
        File dir = getExternalFilesDir(null);
        if (dir == null) {
            Toast.makeText(this, "Folder tidak tersedia", Toast.LENGTH_SHORT).show();
            return;
        }
        File csvFile = new File(dir, "siswa.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Nama,NIM,Jurusan,Kelas,Alamat,No.Telp\n");
            for (Student s : students) {
                writer.append("\"").append(escapeCsv(s.getName())).append("\",");
                writer.append("\"").append(escapeCsv(s.getNim())).append("\",");
                writer.append("\"").append(escapeCsv(s.getMajor())).append("\",");
                writer.append("\"").append(escapeCsv(s.getClassName())).append("\",");
                writer.append("\"").append(escapeCsv(s.getAddress())).append("\",");
                writer.append("\"").append(escapeCsv(s.getPhone())).append("\n");
            }
            writer.flush();
            Toast.makeText(this, "Tereksport ke: " + csvFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Export gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String escapeCsv(String input) {
        if (input == null) return "";
        return input.replace("\"", "\"\"");
    }
}