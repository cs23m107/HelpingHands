package com.example.helpinghands;

public class clothesinfo{
    String address,mobile,clothesimage;
    public clothesinfo(){};

    public clothesinfo(String address, String mobile,String clothesimage) {
        this.address = address;
        this.mobile = mobile;
        this.clothesimage=clothesimage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClothesimage() {
        return clothesimage;
    }

    public void setClothesimage(String clothesimage) {
        this.clothesimage = clothesimage;
    }
}


