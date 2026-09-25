package com.mastagohim.bukukenangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "buku_kenangan.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_ENTRIES = "entries";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_TEACHERS = "teachers";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Entries Table
        String CREATE_ENTRIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ENTRIES + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "date TEXT NOT NULL,"
                + "note TEXT NOT NULL,"
                + "created_at TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_ENTRIES_TABLE);

        // Students Table
        String CREATE_STUDENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "nim TEXT,"
                + "major TEXT,"
                + "class TEXT,"
                + "address TEXT,"
                + "phone TEXT,"
                + "photo_uri TEXT,"
                + "created_at TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

        // Teachers Table
        String CREATE_TEACHERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TEACHERS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "nip TEXT,"
                + "subject TEXT,"
                + "address TEXT,"
                + "phone TEXT,"
                + "photo_uri TEXT,"
                + "created_at TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_TEACHERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add teachers table if upgrading from v1/v2
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TEACHERS + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "nip TEXT,"
                    + "subject TEXT,"
                    + "address TEXT,"
                    + "phone TEXT,"
                    + "photo_uri TEXT,"
                    + "created_at TEXT NOT NULL"
                    + ")");
        }
    }

    // Entry CRUD
    public long createEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", entry.getTitle());
        values.put("date", entry.getDate());
        values.put("note", entry.getNote());
        values.put("created_at", new Date().toString());
        long id = db.insert(TABLE_ENTRIES, null, values);
        db.close();
        return id;
    }

    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ENTRIES, null, null, null, null, null, "created_at DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Entry entry = new Entry();
                entry.setId(cursor.getInt(0));
                entry.setTitle(cursor.getString(1));
                entry.setDate(cursor.getString(2));
                entry.setNote(cursor.getString(3));
                entry.setCreated_at(cursor.getString(4));
                entries.add(entry);
            }
            cursor.close();
        }
        return entries;
    }

    public Entry getEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ENTRIES, null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Entry entry = new Entry();
            entry.setId(cursor.getInt(0));
            entry.setTitle(cursor.getString(1));
            entry.setDate(cursor.getString(2));
            entry.setNote(cursor.getString(3));
            entry.setCreated_at(cursor.getString(4));
            cursor.close();
            return entry;
        }
        return null;
    }

    public int updateEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", entry.getTitle());
        values.put("date", entry.getDate());
        values.put("note", entry.getNote());
        return db.update(TABLE_ENTRIES, values, "id = ?",
                new String[]{String.valueOf(entry.getId())});
    }

    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ENTRIES, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Student CRUD
    public long saveStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("nim", student.getNim());
        values.put("major", student.getMajor());
        values.put("class", student.getClassName());
        values.put("address", student.getAddress());
        values.put("phone", student.getPhone());
        values.put("photo_uri", student.getPhotoUri());
        values.put("created_at", new Date().toString());
        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return id;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, "created_at DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setNim(cursor.getString(2));
                student.setMajor(cursor.getString(3));
                student.setClassName(cursor.getString(4));
                student.setAddress(cursor.getString(5));
                student.setPhone(cursor.getString(6));
                student.setPhotoUri(cursor.getString(7));
                student.setCreatedAt(cursor.getString(8));
                students.add(student);
            }
            cursor.close();
        }
        return students;
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Student student = new Student();
            student.setId(cursor.getInt(0));
            student.setName(cursor.getString(1));
            student.setNim(cursor.getString(2));
            student.setMajor(cursor.getString(3));
            student.setClassName(cursor.getString(4));
            student.setAddress(cursor.getString(5));
            student.setPhone(cursor.getString(6));
            student.setPhotoUri(cursor.getString(7));
            student.setCreatedAt(cursor.getString(8));
            cursor.close();
            return student;
        }
        return null;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("nim", student.getNim());
        values.put("major", student.getMajor());
        values.put("class", student.getClassName());
        values.put("address", student.getAddress());
        values.put("phone", student.getPhone());
        values.put("photo_uri", student.getPhotoUri());
        return db.update(TABLE_STUDENTS, values, "id = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Teacher CRUD
    public long saveTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("nip", teacher.getNip());
        values.put("subject", teacher.getSubject());
        values.put("address", teacher.getAddress());
        values.put("phone", teacher.getPhone());
        values.put("photo_uri", teacher.getPhotoUri());
        values.put("created_at", new Date().toString());
        long id = db.insert(TABLE_TEACHERS, null, values);
        db.close();
        return id;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHERS, null, null, null, null, null, "created_at DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(0));
                teacher.setName(cursor.getString(1));
                teacher.setNip(cursor.getString(2));
                teacher.setSubject(cursor.getString(3));
                teacher.setAddress(cursor.getString(4));
                teacher.setPhone(cursor.getString(5));
                teacher.setPhotoUri(cursor.getString(6));
                teachers.add(teacher);
            }
            cursor.close();
        }
        return teachers;
    }

    public Teacher getTeacher(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHERS, null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Teacher teacher = new Teacher();
            teacher.setId(cursor.getInt(0));
            teacher.setName(cursor.getString(1));
            teacher.setNip(cursor.getString(2));
            teacher.setSubject(cursor.getString(3));
            teacher.setAddress(cursor.getString(4));
            teacher.setPhone(cursor.getString(5));
            teacher.setPhotoUri(cursor.getString(6));
            cursor.close();
            return teacher;
        }
        return null;
    }
}