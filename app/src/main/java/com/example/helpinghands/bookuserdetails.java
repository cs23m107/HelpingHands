package com.example.helpinghands;

public class bookuserdetails {
    private String address,mobile,PhotoUrl,CurrentUserId;

    public bookuserdetails(String address, String mobile, String photoUrl, String currentUserId) {
        this.address = address;
        this.mobile = mobile;
        this.PhotoUrl = photoUrl;
        CurrentUserId = currentUserId;
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

    public String getPhotoUrl() {
        return this.PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.PhotoUrl = photoUrl;
    }

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        CurrentUserId = currentUserId;
    }
}
