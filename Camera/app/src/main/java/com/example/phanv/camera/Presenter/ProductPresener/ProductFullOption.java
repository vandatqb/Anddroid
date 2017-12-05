package com.example.phanv.camera.Presenter.ProductPresener;

import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.ProductModel.ProductProcess;

/**
 * Created by phanv on 01-Dec-17.
 */

public class ProductFullOption {
    ProductProcess process = new ProductProcess();
    public Boolean addProdut(Product product,String id){
        return process.addProduct(product,id);
    }
}
