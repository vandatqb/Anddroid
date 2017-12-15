package com.example.phanv.camera.View.SearchView;

import com.example.phanv.camera.Model.ProductModel.Product;

import java.util.List;

/**
 * Created by phanv on 15-Dec-17.
 */

public interface SearchInterface {
    void getCountSearchSuccess(int count);
    void getListSearchSuccess(List<Product> list);
}
