package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.ProductModel.Maker;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.ProductModel.ProductProcess;

import java.util.List;

/**
 * Created by phanv on 01-Dec-17.
 */

public class ProductPresenter {
    public ProductProcess process = new ProductProcess();

    public List<Product> getAllProductWithId(String id) {
        return process.getAllProductWithId(id);
    }

    public List<Product> getTopNewProduct() {
        return process.getTopNewProduct();
    }

    public List<Product> getTopNikonProduct() {
        return process.getTopNikonProduct();
    }

    public List<Product> getTopCanonProduct() {
        return process.getTopCanonProduct();
    }

    public Product getProductInfor(String id) {
        return process.getProductInfor(id);
    }

    public boolean addProduct(Product product, String id) {
        return process.addProduct(product, id);
    }

    public boolean deleteProduct(String idProduct) {
        return process.deleteProduct(idProduct);
    }

    public List<Maker> getListMaker() {
        return process.getListMaker();
    }

    public List<Maker> getListVideo() {
        return process.getListVideo();
    }

    public boolean updateProduct(Product product) {
        return process.updateProduct(product);
    }

    public void addOrRemoveFavourite(String idAccount, String idProduct) {
        process.addOrRemoveFavourite(idAccount, idProduct);
    }

    public List<Product> getTopUnderTenMillion() {
        return process.getTopUnderTenMillion();
    }

    public List<Product> getFavouriteProduct(String id) {
        return process.getFavouriteProduct(id);
    }
}
