package com.example.helpinghands;

public class booksinfo{
    String address,mobile,booksimage;
    public booksinfo(){};

    public booksinfo(String address, String mobile,String booksimage) {
        this.address = address;
        this.mobile = mobile;
        this.booksimage=booksimage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getBooksimage() {
        return booksimage;
    }

    public void setBooksimage(String booksimage) {
        this.booksimage = booksimage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}


