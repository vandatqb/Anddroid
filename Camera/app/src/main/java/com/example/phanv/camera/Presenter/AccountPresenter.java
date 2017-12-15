package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountProcess;

/**
 * Created by phanv on 13-Dec-17.
 */

public class AccountPresenter {
    public AccountProcess process = new AccountProcess();

    public Account getAccountInformation(String id) {
        return process.getAccountInformation(id);
    }

    public int upPass(String id, String oldPass, String newPass) {
        return process.upPass(id, oldPass, newPass);
    }

    public int updateInfor(String id, String fullName, String phone, String address, String email, String linkDown) {
        return process.updateInfor(id, fullName, phone, address, email, linkDown);
    }

    public int checkLogin(String name, String pass) {
        return process.checkLogin(name, pass);
    }

    public Boolean checkLoginName(String loginName) {
        return process.chekLoginName(loginName);
    }

    public int register(String loginName, String fullName, String phone, String pass, String address, String email, String image) {
        return process.register(loginName, fullName, phone, pass, address, email, image);
    }
}
