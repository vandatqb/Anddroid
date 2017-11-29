package com.example.phanv.camera.Process;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Model.Property;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 28-Nov-17.
 */

public class LocalData {
    Activity activity;
    ConnectServer connect;

    public LocalData(Activity activity) {
        this.activity =activity;
    }
    public Local read()
    {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = store.getString("id","0");
        String loginName = store.getString("loginName","0");
        String fullName = store.getString("fullName","0");
        String image = store.getString("image","0");
        Local  local = new Local(id,loginName,fullName,image);
        return local;
    }
    public void write(String id, String loginName,String fullName,String image)
    {
        SharedPreferences store = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = store.edit();
        editor.putString("id",id);
        editor.putString("loginName",loginName);
        editor.putString("fullName",fullName);
        editor.putString("image",image);
        editor.commit();
    }
    public void writeId(String id)
    {
        List<Property> list = new ArrayList<>();
        Property loginId = new Property("idlogin",id);
        list.add(loginId);
        String address= "http://tempuri.org/getAccountInfor";
        String action = "getAccountInfor";
        SoapObject object = connect.process(list,address,action);
        if(object!=null){
            SoapObject infor = (SoapObject) object.getProperty(0);
            String idAcount= infor.getPropertyAsString("Id_account");
            String loginName=infor.getPropertyAsString("Login_name");
            String fullName=infor.getPropertyAsString("Full_name");
            String image =infor.getPropertyAsString("Image");
            write(idAcount,loginName,fullName,image);
        }
    }
}
