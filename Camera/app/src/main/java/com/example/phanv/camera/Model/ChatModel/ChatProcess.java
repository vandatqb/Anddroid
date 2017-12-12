package com.example.phanv.camera.Model.ChatModel;

import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class ChatProcess {
    ConnectServer connect = new ConnectServer();

    public List<ListCameraChat> getListChat(String idAccount) {
        List<ListCameraChat> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        Property pr = new Property("id", idAccount);
        list.add(pr);
        String soapAddress = "http://tempuri.org/getListChat";
        String action = "getListChat";
        SoapObject object = connect.process(list, soapAddress, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject obj = (SoapObject) object.getProperty(i);
                String image = obj.getProperty("Image").toString();
                String name = obj.getProperty("Full_name").toString();
                String id = obj.getProperty("Id").toString();
                String content = obj.getProperty("Content").toString();
                String time = obj.getProperty("Time").toString();
                ListCameraChat detail = new ListCameraChat(image, name, content, time, id);
                result.add(detail);
            }
        }
        return result;
    }

    public String getNode(String idSend, String idReceive) {
        String result = "";
        List<Property> list = new ArrayList<>();
        Property idSend1 = new Property("idsend", idSend);
        Property idRec = new Property("idreceive", idReceive);
        list.add(idSend1);
        list.add(idRec);
        String address = "http://tempuri.org/getNode";
        String action = "getNode";
        result = connect.processString(list, address, action);
        return result;
    }

    public List<Chat> getChatDetail(String idSend, String idReceive) {
        List<Chat> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        Property idSen = new Property("idsend", idSend);
        Property idRec = new Property("idreceive", idReceive);
        list.add(idSen);
        list.add(idRec);
        String address = "http://tempuri.org/getChatDetail";
        String action = "getChatDetail";
        SoapObject object = connect.process(list, address, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject chat = (SoapObject) object.getProperty(i);
                String idSend1 = chat.getProperty("Id_send").toString();
                String idReceive1 = chat.getProperty("Id_receive").toString();
                String time = chat.getProperty("Time").toString();
                String content = chat.getProperty("Content").toString();
                Chat detail = new Chat(idSend1, idReceive1, time, content);
                result.add(detail);
            }
        }
        return result;
    }

    public boolean addChat(String idSend, String idReceive, String content) {
        boolean result = false;
        List<Property> list = new ArrayList<>();
        Property idSend1 = new Property("idsend", idSend);
        Property idRec = new Property("idreceive", idReceive);
        Property con = new Property("content", content);
        list.add(idSend1);
        list.add(idRec);
        list.add(con);
        String address = "http://tempuri.org/addChat";
        String action = "addChat";
        try {
            connect.process(list, address, action);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
