package com.example.phanv.camera.Model.AccountModel;

import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 29-Nov-17.
 */

public class AccountProcess {
    ConnectServer connect = new ConnectServer();

    public Account getAccountInformation(String id) {
        Account account = new Account();
        List<Property> list = new ArrayList<>();
        Property loginId = new Property("idlogin", id);
        list.add(loginId);
        String address = "http://tempuri.org/getAccountInfor";
        String action = "getAccountInfor";
        SoapObject object = connect.process(list, address, action);
        if (object != null) {
            String idAcount = object.getProperty("Id_account").toString();
            String fullName = object.getProperty("Full_name").toString();
            String phone = object.getProperty("Phone").toString();
            String add = object.getProperty("Address").toString();
            String email = object.getProperty("Email").toString();
            String image = object.getProperty("Image").toString();
            String type = object.getProperty("Type").toString();
            String status = object.getProperty("Status").toString();
            String loginName = object.getProperty("Login_name").toString();
            account = new Account(idAcount, loginName, fullName, phone, add, email, image, type, status);
        }
        return account;
    }

    public int upPass(String id, String oldPass, String newPass) {
        int result;
        List<Property> list = new ArrayList<>();
        Property loginId = new Property("id", id);
        Property old = new Property("oldPass", oldPass);
        Property newPassword = new Property("newpass", newPass);
        list.add(loginId);
        list.add(old);
        list.add(newPassword);
        String address = "http://tempuri.org/changePass";
        String action = "changePass";
        String resultSever = connect.processString(list, address, action);
        try {
            result = Integer.parseInt(resultSever);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    public int updateInfor(String id, String fullName, String phone, String address, String email, String linkDown) {
        int result;
        List<Property> list = new ArrayList<>();
        Property prId = new Property("id", id);
        Property prFullName = new Property("fullName", fullName);
        Property prPhone = new Property("phone", phone);
        Property prAddress = new Property("address", address);
        Property prEmail = new Property("email", email);
        Property prImage = new Property("image", linkDown);
        list.add(prId);
        list.add(prFullName);
        list.add(prPhone);
        list.add(prAddress);
        list.add(prEmail);
        list.add(prImage);
        String add = "http://tempuri.org/updateAccountInfor";
        String action = "updateAccountInfor";
        try {
            result = Integer.parseInt(connect.processString(list, add, action));
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }
    public int checkLogin(String name, String pass) {
        List<Property> list = new ArrayList<>();
        Property loginName = new Property("loginName", name);
        Property password = new Property("pass", pass);
        list.add(loginName);
        list.add(password);
        String address = "http://tempuri.org/checkLogin";
        String action = "checkLogin";
        String id = connect.processString(list, address, action);
        if (id.equals("er")) {
            return -1;
        } else {
            if (id.equals("0")) {
                return 0;
            } else {
                return Integer.parseInt(id);
            }
        }
    }
}
