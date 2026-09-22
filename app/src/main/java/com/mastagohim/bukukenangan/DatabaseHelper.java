package com.mastagohim.bukukenangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper untuk penyimpanan offline Buku Kenangan
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "buku_kenangan.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel untuk data anggota
    private static final String TABLE_MEMBERS = "members";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_QR_CODE = "qr_code";
    public static final String COL_REG_DATE = "reg_date";
    public static final String COL_NOTES = "notes";

    // Tabel untuk riwayat
    private static final String TABLE_HISTORY = "history";
    public static final String HIST_ID = "id";
    public static final String HIST_MEMBER_ID = "member_id";
    public static final String HIST_ACTION = "action";
    public static final String HIST_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_EMAIL + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_QR_CODE + " TEXT UNIQUE,"
                + COL_REG_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + COL_NOTES + " TEXT" + ")";

        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + HIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HIST_MEMBER_ID + " INTEGER,"
                + HIST_ACTION + " TEXT,"
                + HIST_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + " FOREIGN KEY(" + HIST_MEMBER_ID + ") REFERENCES " + TABLE_MEMBERS + "(" + COL_ID + ")" + ")";

        db.execSQL(CREATE_MEMBERS_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // Tambah member
    public long addMember(String name, String email, String phone, String qrCode, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_QR_CODE, qrCode);
        values.put(COL_NOTES, notes);
        long id = db.insert(TABLE_MEMBERS, null, values);
        db.close();
        return id;
    }

    // Dapatkan semua member
    public Cursor getAllMembers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MEMBERS, null, null, null, null, null, COL_NAME + " ASC");
    }

    // Cari member by QR code
    public Cursor getMemberByQR(String qrCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MEMBERS, null, COL_QR_CODE + "=?", 
                new String[]{qrCode}, null, null, null);
    }

    // Tambah riwayat
    public long addHistory(long memberId, String action) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HIST_MEMBER_ID, memberId);
        values.put(HIST_ACTION, action);
        long id = db.insert(TABLE_HISTORY, null, values);
        db.close();
        return id;
    }

    // Dapatkan riwayat member
    public Cursor getHistoryByMember(long memberId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_HISTORY, null, HIST_MEMBER_ID + "=?",
                new String[]{String.valueOf(memberId)}, null, null, HIST_TIMESTAMP + " DESC");
    }

    // Export semua data ke JSON (untuk backup)
    public String exportAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEMBERS, null, null, null, null, null, null);
        
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"members\":[");
        
        boolean first = true;
        while (cursor.moveToNext()) {
            if (!first) sb.append(",");
            sb.append("{");
            sb.append "\""+COL_ID+"\":").append(cursor.getLong(cursor.getColumnIndex(COL_ID))).append(",");
            sb.append "\""+COL_NAME+"\":\"").append(cursor.getString(cursor.getColumnIndex(COL_NAME))).append("\",");
            sb.append "\""+COL_PHONE+"\":\"").append(cursor.getString(cursor.getColumnIndex(COL_PHONE))).append("\",");
            sb.append "\""+COL_QR_CODE+"\":\"").append(cursor.getString(cursor.getColumnIndex(COL_QR_CODE))).append("\"");
            sb.append("}");
            first = false;
        }
        sb.append("]");
        sb.append("}");
        
        cursor.close();
        db.close();
        return sb.toString();
    }
}