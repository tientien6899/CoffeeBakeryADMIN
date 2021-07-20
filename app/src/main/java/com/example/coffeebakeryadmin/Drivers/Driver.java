package com.example.coffeebakeryadmin.Drivers;

public class Driver {
    String driverid;
    String driverName;
    String driverPhone;
    String gmail;
    String avatar;

    public Driver() {
    }

    public Driver(String driverid, String driverName, String driverPhone, String gmail, String avatar) {
        this.driverid = driverid;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.gmail = gmail;
        this.avatar = avatar;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
