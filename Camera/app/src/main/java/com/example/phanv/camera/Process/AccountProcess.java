package com.example.phanv.camera.Process;

import com.example.phanv.camera.Model.Account;
import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.Property;

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
}
