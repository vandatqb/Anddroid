package com.example.phanv.camera.Process;

import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.Product;
import com.example.phanv.camera.Model.Property;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 30-Nov-17.
 */

public class ProductProcess {

    ConnectServer coonnect = new ConnectServer();

    public ProductProcess() {
    }

    public List<Product> getAllProductWithId(String id) {
        List<Product> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        Property prId = new Property("id", id);
        list.add(prId);
        String address = "http://tempuri.org/getListProductWithId";
        String action = "getListProductWithId";
        SoapObject object = coonnect.process(list, address, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject ob = (SoapObject) object.getProperty(i);
                String idProduct = ob.getProperty("Id_produc").toString();
                String idAccount = ob.getProperty("Id_account").toString();
                String name = ob.getProperty("Name").toString();
                String nameMaker = ob.getProperty("Name_maker").toString();
                String idMaker = ob.getProperty("Id_maker").toString();
                String type = ob.getProperty("Type").toString();
                String mega = ob.getProperty("Mega").toString();
                String nameVideo = ob.getProperty("Name_video").toString();
                String video = ob.getProperty("Video").toString();
                String addition = ob.getProperty("Addition").toString();
                String price = ob.getProperty("Price").toString();
                String time = ob.getProperty("Time").toString();
                String status = ob.getProperty("Status").toString();
//                String img = ob.getProperty("image").toString();
//                String img1 = ob.getProperty("image1").toString();
//                String img2 = ob.getProperty("image2").toString();
                Product product = new Product(idProduct, idAccount, name, idMaker, nameMaker, type, mega, video, nameVideo, addition, price, time, status, "", "", "");
                result.add(product);
            }
        }
        return result;
    }
}
