package com.mastagohim.bukukenangan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        // Add sample entries if empty
        List<Entry> entries = dbHelper.getAllEntries();
        if (entries.size() == 0) {
            dbHelper.createEntry(new Entry("Kelas 10 TKJ", "2024-09-20", "Catatan pertama saya"));
            dbHelper.createEntry(new Entry("Kuis Matematika", "2024-09-19", "Nilai 85"));
        }

        List<Entry> allEntries = dbHelper.getAllEntries();
        EntryAdapter adapter = new EntryAdapter(this, allEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}