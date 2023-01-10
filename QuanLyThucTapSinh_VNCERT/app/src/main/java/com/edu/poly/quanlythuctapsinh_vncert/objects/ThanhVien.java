package com.edu.poly.quanlythuctapsinh_vncert.objects;

public class ThanhVien {
    private int id;
    private String name;
    private String gender;
    private String numberphone;
    private String gmail;
    private String address;

    public ThanhVien() {
    }

    public ThanhVien(int id, String name, String gender, String numberphone, String gmail, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.numberphone = numberphone;
        this.gmail = gmail;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
