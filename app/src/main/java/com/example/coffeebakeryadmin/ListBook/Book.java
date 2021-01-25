package com.example.coffeebakeryadmin.ListBook;

public class Book {
    String tieude, ngaydang, noidung;

    public Book() {
    }

    public Book(String tieude, String ngaydang, String noidung) {
        this.tieude = tieude;
        this.ngaydang = ngaydang;
        this.noidung = noidung;
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
}
