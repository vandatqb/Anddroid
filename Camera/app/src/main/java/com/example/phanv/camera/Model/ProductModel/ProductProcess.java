package com.example.phanv.camera.Model.ProductModel;

import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;

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
                Product product = new Product(idProduct, idAccount, name, idMaker, nameMaker, type, mega, video, nameVideo, addition, price, time, status, img, img1, img2,des);
                result.add(product);
            }
        }
        return result;
    }

    public boolean addProduct(Product product, String id) {
        int resultConnect;
        List<Property> list = new ArrayList<>();
        Property id_account = new Property("id_account", id);
        Property name = new Property("name", product.getName());
        Property id_maker = new Property("id_maker",product.getIdMaker());
        Property type = new Property("type", product.getType());
        Property megga = new Property("mega", product.getMega());
        Property video = new Property("video", product.getVideo());
        Property add = new Property("add", product.getAddition());
        Property price = new Property("price", product.getPrice());
        Property time = new Property("time", product.getTime());
        Property image = new Property("image", product.getImage());
        Property image1 = new Property("image1", product.getImage1());
        Property image2 = new Property("image2", product.getImage2());
        Property status = new Property("status", product.getStatus());
        Property description = new Property("dess", product.getDescription());

        list.add(id_account);
        list.add(name);
        list.add(id_maker);
        list.add(type);
        list.add(megga);
        list.add(video);
        list.add(add);
        list.add(price);
        list.add(time);
        list.add(image);
        list.add(image1);
        list.add(image2);
        list.add(status);
        list.add(description);

        String address = "http://tempuri.org/addProduct";
        String action = "addProduct";
        try {
            String re = coonnect.processString(list, address, action);
            resultConnect = Integer.parseInt(re);
        } catch (Exception e) {
            resultConnect = 0;
        }
        if (resultConnect > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Maker> getListMaker() {
        List<Maker> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        String address = "http://tempuri.org/getListHang";
        String action = "getListHang";
        SoapObject object = coonnect.process(list, address, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject soapObject = (SoapObject) object.getProperty(i);
                Maker maker = new Maker(soapObject.getProperty("Id_maker").toString(), soapObject.getProperty("Name_maker").toString());
                result.add(maker);
            }
        }
        return result;
    }
    public List<Maker> getListVideo() {
        List<Maker> result = new ArrayList<>();
        List<Property> list = new ArrayList<>();
        String address = "http://tempuri.org/getListVideo";
        String action = "getListVideo";
        SoapObject object = coonnect.process(list, address, action);
        if (object != null) {
            for (int i = 0; i < object.getPropertyCount(); i++) {
                SoapObject soapObject = (SoapObject) object.getProperty(i);
                Maker maker = new Maker(soapObject.getProperty("Id_maker").toString(), soapObject.getProperty("Name_maker").toString());
                result.add(maker);
            }
        }
        return result;
    }
}
