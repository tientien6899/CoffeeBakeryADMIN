package com.example.coffeebakeryadmin.List_Receipt;

public class Receipt {
    String madon;
    String ngaydat;
    String tongtien;
    String trangthai;
    String nguoidung;

    public Receipt() {
    }

    public Receipt(String madon, String ngaydat, String tongtien, String trangthai, String nguoidung) {
        this.madon = madon;
        this.ngaydat = ngaydat;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
        this.nguoidung = nguoidung;
    }

    public String getMadon() {
        return madon;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
    public String getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(String nguoidung) {
        this.nguoidung = nguoidung;
    }
}
