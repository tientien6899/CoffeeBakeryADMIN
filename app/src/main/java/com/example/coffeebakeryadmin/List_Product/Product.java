package com.example.coffeebakeryadmin.List_Product;

public class Product {
    String tensp;
    String danhmuc;
    String link;
    String masp;
    String giaS;
    String giaM;
    String giaL;
    String giaKM;
    String mota;
    String ngaydang;

    public Product() {

    }

    public Product(String tensp, String danhmuc, String link, String masp, String giaS, String giaM, String giaL, String giaKM, String mota, String ngaydang) {
        this.tensp = tensp;
        this.danhmuc = danhmuc;
        this.link = link;
        this.masp = masp;
        this.giaS = giaS;
        this.giaM = giaM;
        this.giaL = giaL;
        this.giaKM = giaKM;
        this.mota = mota;
        this.ngaydang = ngaydang;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getGiaS() {
        return giaS;
    }

    public void setGiaS(String giaS) {
        this.giaS = giaS;
    }

    public String getGiaM() {
        return giaM;
    }

    public void setGiaM(String giaM) {
        this.giaM = giaM;
    }

    public String getGiaL() {
        return giaL;
    }

    public void setGiaL(String giaL) {
        this.giaL = giaL;
    }

    public String getGiaKM() {
        return giaKM;
    }

    public void setGiaKM(String giaKM) {
        this.giaKM = giaKM;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }


    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }
}
