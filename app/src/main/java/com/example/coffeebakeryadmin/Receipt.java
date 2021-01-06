package com.example.coffeebakeryadmin;

public class Receipt {
    String madon, magh, ngaydat, tongtien, trangthai;

    public Receipt() {
    }

    public Receipt(String madon, String magh, String ngaydat, String tongtien, String trangthai) {
        this.madon = madon;
        this.magh = magh;
        this.ngaydat = ngaydat;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getMadon() {
        return madon;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public String getMagh() {
        return magh;
    }

    public void setMagh(String magh) {
        this.magh = magh;
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
}
