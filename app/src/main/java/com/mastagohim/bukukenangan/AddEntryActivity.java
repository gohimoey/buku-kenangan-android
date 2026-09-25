package com.mastagohim.bukukenangan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEntryActivity extends AppCompatActivity {
    private EditText editTitle, editDate, editNote;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        editTitle = findViewById(R.id.edit_title);
        editDate = findViewById(R.id.edit_date);
        editNote = findViewById(R.id.edit_note);
        btnSave = findViewById(R.id.btn_save);
        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String date = editDate.getText().toString();
                String note = editNote.getText().toString();

                if (!title.isEmpty() && !date.isEmpty()) {
                    dbHelper.createEntry(new Entry(title, date, note));
                    Toast.makeText(AddEntryActivity.this, "Entry disimpan!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddEntryActivity.this, "Judul dan tanggal wajib diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}