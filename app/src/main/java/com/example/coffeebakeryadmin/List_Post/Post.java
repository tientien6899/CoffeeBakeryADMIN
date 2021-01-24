package com.example.coffeebakeryadmin.List_Post;

public class Post {
    String tieude, ngaydang, noidung, hinhanh;

    public Post() {
    }

    public Post(String tieude, String ngaydang, String noidung, String hinhanh) {
        this.tieude = tieude;
        this.ngaydang = ngaydang;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
