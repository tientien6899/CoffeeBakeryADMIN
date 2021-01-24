package com.example.coffeebakeryadmin.Infomation;

public class Info {
    String tencuahang, email, diachi, sdt, ghichu;

    public Info() {
    }

    public Info(String tencuahang, String email, String diachi, String sdt, String ghichu) {
        this.tencuahang = tencuahang;
        this.email = email;
        this.diachi = diachi;
        this.sdt = sdt;
        this.ghichu = ghichu;
    }

    public String getTencuahang() {
        return tencuahang;
    }

    public void setTencuahang(String tencuahang) {
        this.tencuahang = tencuahang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
