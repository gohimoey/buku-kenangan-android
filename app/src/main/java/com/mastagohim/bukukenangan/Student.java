package com.mastagohim.bukukenangan;

public class Student {
    private int id;
    private String name;
    private String nim;
    private String major;
    private String className;
    private String address;
    private String phone;
    private String photoUri;
    private String createdAt;

    public Student() {}

    public Student(String name, String nim, String major, String className, String address, String phone) {
        this.name = name;
        this.nim = nim;
        this.major = major;
        this.className = className;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}