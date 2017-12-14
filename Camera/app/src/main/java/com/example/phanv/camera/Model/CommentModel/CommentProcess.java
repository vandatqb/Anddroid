package com.example.phanv.camera.Model.CommentModel;

import com.example.phanv.camera.Model.ServerModel.Property;
import com.example.phanv.camera.Presenter.SeverPresenter;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 14-Dec-17.
 */

public class CommentProcess {
    SeverPresenter connect = new SeverPresenter();

    public int addComment(String idAccount, String idComment, String content, String start) {
        List<Property> list = new ArrayList<>();
        Property prIdAccount = new Property("id_account", idAccount);
        Property prIdComment = new Property("id_comment", idComment);
        Property prTime = new Property("content", content);
        Property prStart = new Property("start", start);
        list.add(prIdAccount);
        list.add(prIdComment);
        list.add(prTime);
        list.add(prStart);
        String soapAddress = "http://tempuri.org/insertComment";
        String action = "insertComment";
        String result = connect.processString(list, soapAddress, action);
        if (result.equals("1")) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<Comment> getListComment(String idAccount) {
        List<Comment> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        Property prIdAccount = new Property("id_accoun", idAccount);
        list.add(prIdAccount);
        String soapAddress = "http://tempuri.org/getListComment";
        String action = "getListComment";
        SoapObject object = connect.process(list, soapAddress, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject obj = (SoapObject) object.getProperty(i);
                String idAc = obj.getProperty("IdAccount").toString();
                String idAccountComment = obj.getProperty("IdAccountComment").toString();
                String content = obj.getProperty("Content").toString();
                String time = obj.getProperty("Time").toString();
                String name = obj.getProperty("Name").toString();
                String img = obj.getProperty("Img").toString();
                String start = obj.getProperty("Start").toString();
                Comment comment = new Comment(idAc, idAccountComment, content, time,name,img,start);
                result.add(comment);
            }
        }
        return result;
    }

    public int getCountComment(String idAccount) {
        int result = 0;
        List<Property> list = new ArrayList<>();
        Property prIdAccount = new Property("id_account", idAccount);
        list.add(prIdAccount);
        String soapAddress = "http://tempuri.org/sumComment";
        String action = "sumComment";
        String resultProcess = connect.processString(list, soapAddress, action);
        try {
            result = Integer.parseInt(resultProcess);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    public float getAvgStart(String idAccount) {
        float result = 0;
        List<Property> list = new ArrayList<>();
        Property prIdAccount = new Property("id_account", idAccount);
        list.add(prIdAccount);
        String soapAddress = "http://tempuri.org/caculateStart";
        String action = "caculateStart";
        String resultProcess = connect.processString(list, soapAddress, action);
        try {
            result = Float.parseFloat(resultProcess);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }
}
