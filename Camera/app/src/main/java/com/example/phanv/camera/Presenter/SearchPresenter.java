package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.SearchModel.SearchProcess;

import java.util.List;

/**
 * Created by phanv on 15-Dec-17.
 */

public class SearchPresenter {
    SearchProcess process = new SearchProcess();

    public int getCountSearch(String keySearch) {
        return process.getCountSearch(keySearch);
    }

    public List<Product> getListSearchProduct(int number, String keySearch) {
        return process.getListSearchProduct(number, keySearch);
    }
}
