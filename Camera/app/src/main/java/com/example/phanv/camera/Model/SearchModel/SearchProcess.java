package com.example.phanv.camera.Model.SearchModel;

import com.example.phanv.camera.Model.ChatModel.ListCameraChat;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.ServerModel.Property;
import com.example.phanv.camera.Presenter.SeverPresenter;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 15-Dec-17.
 */

public class SearchProcess {
    SeverPresenter connect = new SeverPresenter();
    public int getCountSearch(String keySearch)
    {
        List<Property> list = new ArrayList<>();
        Property pr = new Property("keySearch", keySearch);
        list.add(pr);
        String soapAddress = "http://tempuri.org/getCountSearch";
        String action = "getCountSearch";
        String object = connect.processString(list, soapAddress, action);
        try {
            return Integer.parseInt(object);
        }
        catch (Exception e){
            return -1;
        }
    }
    public List<Product> getListSearchProduct(int number,String keySearch)
    {
        List<Product> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        Property prNumber = new Property("number", number+"");
        Property prkeySearch = new Property("keySearch", keySearch);
        list.add(prNumber);
        list.add(prkeySearch);
        String address = "http://tempuri.org/getListSearchProduct";
        String action = "getListSearchProduct";
        SoapObject object = connect.process(list, address, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject ob = (SoapObject) object.getProperty(i);
                String idProduct = ob.getProperty("IdProduct").toString();
                String idAccount = ob.getProperty("IdAccount").toString();
                String name = ob.getProperty("Name").toString();
                String nameMaker = ob.getProperty("NameMaker").toString();
                String idMaker = ob.getProperty("IdMaker").toString();
                String type = ob.getProperty("Type").toString();
                String mega = ob.getProperty("Mega").toString();
                String nameVideo = ob.getProperty("NameVideo").toString();
                String video = ob.getProperty("IdVideo").toString();
                String addition = ob.getProperty("Addition").toString();
                String price = ob.getProperty("Price").toString();
                String time = ob.getProperty("Time").toString();
                String status = ob.getProperty("Status").toString();
                String img = ob.getProperty("Img").toString();
                String img1 = ob.getProperty("Image").toString();
                String img2 = ob.getProperty("Image3").toString();
                String des = ob.getProperty("Description").toString();
                Product product = new Product(idProduct, idAccount, name, idMaker, nameMaker, type, mega, video, nameVideo, addition, price, time, status, img, img1, img2, des);
                result.add(product);
            }
        }
        return result;
    }
}
