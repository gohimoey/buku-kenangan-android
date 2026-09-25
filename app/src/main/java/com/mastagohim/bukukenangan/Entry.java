package com.mastagohim.bukukenangan;

public class Entry {
    private int id;
    private String title;
    private String date;
    private String note;
    private String created_at;

    public Entry() {}

    public Entry(String title, String date, String note) {
        this.title = title;
        this.date = date;
        this.note = note;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}