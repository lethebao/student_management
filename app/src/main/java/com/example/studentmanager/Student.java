package com.example.studentmanager;

public class Student {
    private String maSV;
    private String tenSV;
    private String maLop;

    public Student(String maSV, String tenSV, String maLop) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.maLop = maLop;
    }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getTenSV() { return tenSV; }
    public void setTenSV(String tenSV) { this.tenSV = tenSV; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    @Override
    public String toString() {
        return "Mã SV: " + maSV + ", Tên: " + tenSV + ", Lớp: " + maLop;
    }
}

