package com.example.phanv.camera.Model.DataLocalModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.AccountModel.AccountProcess;

/**
 * Created by phanv on 28-Nov-17.
 */

public class LocalData {
    Activity activity;
    ConnectServer connect = new ConnectServer();

    public LocalData(Activity activity) {
        this.activity = activity;
    }

    public Local read() {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = store.getString("id", "0");
        String loginName = store.getString("loginName", "0");
        String fullName = store.getString("fullName", "0");
        String image = store.getString("image", "0");
        Local local = new Local(id, loginName, fullName, image);
        return local;
    }

    public void write(String id, String loginName, String fullName, String image) {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        editor.putString("id", id);
        editor.putString("loginName", loginName);
        editor.putString("fullName", fullName);
        editor.putString("image", image);
        editor.commit();
    }

    public void writeId(String id) {
        AccountProcess ac = new AccountProcess();
        Account account = ac.getAccountInformation(id);
        if (account != null) {
            write(account.getIdAccount(), account.getLoginName(), account.getFullName(), account.getImage());
        }
    }
}
