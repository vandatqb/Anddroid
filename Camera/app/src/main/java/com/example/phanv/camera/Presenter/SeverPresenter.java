package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;

import org.ksoap2.serialization.SoapObject;

import java.util.List;

/**
 * Created by phanv on 13-Dec-17.
 */

public class SeverPresenter {
    public ConnectServer connect = new ConnectServer();
    public String processString(List<Property> list, final String soap, final String operation){
        return connect.processString(list,soap,operation);
    }
    public SoapObject process(List<Property> list, final String soap, final String operation){
        return connect.process(list,soap,operation);
    }
}
