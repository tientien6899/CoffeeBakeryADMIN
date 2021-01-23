package com.example.coffeebakeryadmin.ListCustomer;

public class Customer {
    String gmail, hoten, sdt, sonha, phuong, quan, thanhpho;

    public Customer() {
    }

    public Customer(String gmail, String hoten, String sdt, String sonha, String phuong, String quan, String thanhpho) {
        this.gmail = gmail;
        this.hoten = hoten;
        this.sdt = sdt;
        this.sonha = sonha;
        this.phuong = phuong;
        this.quan = quan;
        this.thanhpho = thanhpho;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSonha() {
        return sonha;
    }

    public void setSonha(String sonha) {
        this.sonha = sonha;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        this.thanhpho = thanhpho;
    }

}
