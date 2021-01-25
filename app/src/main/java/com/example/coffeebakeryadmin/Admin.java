package com.example.coffeebakeryadmin;

public class Admin {
    String hoten, email, mkhientai;

    public Admin() {
    }

    public Admin(String hoten, String email, String mkhientai) {
        this.hoten = hoten;
        this.email = email;
        this.mkhientai = mkhientai;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMkhientai() {
        return mkhientai;
    }

    public void setMkhientai(String mkhientai) {
        this.mkhientai = mkhientai;
    }
}
