package com.example.phanv.camera.Model.DataLocalModel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountProcess;
import com.example.phanv.camera.Model.ProductModel.Maker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 28-Nov-17.
 */

public class DataLocalProcess {
    public Activity activity;

    public DataLocalProcess(Activity activity) {
        this.activity = activity;
    }

    public AccountInformation read() {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = store.getString("id", "0");
        String loginName = store.getString("loginName", "0");
        String fullName = store.getString("fullName", "0");
        String image = store.getString("image", "0");
        AccountInformation accountInformation = new AccountInformation(id, loginName, fullName, image);
        return accountInformation;
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

    public void writeListMaker(List<Maker> list) {

        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("maker", json);
        editor.commit();
    }

    public List<Maker> readListMaker() {
        List<Maker> list = new ArrayList<>();
        try {
            SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = store.getString("maker", null);

            Type type = new TypeToken<List<Maker>>() {
            }.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void writeListVideo(List<Maker> list) {

        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("video", json);
        editor.commit();
    }

    public List<Maker> readListVideo() {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        Gson gson = new Gson();
        String json = store.getString("video", null);
        List<Maker> list = new ArrayList<>();
        Type type = new TypeToken<List<Maker>>() {
        }.getType();
        list = gson.fromJson(json, type);
        return list;
    }
}
