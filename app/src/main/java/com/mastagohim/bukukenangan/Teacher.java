package com.mastagohim.bukukenangan;

public class Teacher {
    private int id;
    private String name;
    private String nip;
    private String subject;
    private String address;
    private String phone;
    private String photoUri;

    public Teacher() { }

    public Teacher(String name, String nip, String subject, String address, String phone) {
        this.name = name;
        this.nip = nip;
        this.subject = subject;
        this.address = address;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
}