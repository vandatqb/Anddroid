package com.example.phanv.camera.Model.AccountModel;

/**
 * Created by phanv on 29-Nov-17.
 */

public class Account {
    private String idAccount;
    private String loginName;
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String image;
    private String tppe;
    private String status;

    public Account() {
    }

    public Account(String idAccount, String loginName, String fullName, String phone, String address, String email, String image, String tppe, String status) {
        this.idAccount = idAccount;
        this.loginName = loginName;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.image = image;
        this.tppe = tppe;
        this.status = status;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTppe() {
        return tppe;
    }

    public void setTppe(String tppe) {
        this.tppe = tppe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
