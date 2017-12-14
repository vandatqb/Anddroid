package com.example.phanv.camera.Model.ProductModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.Other.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class GetListMenuProductTask extends AsyncTask<Integer, List<Product>, List<Product>> {
    ProductPresenter process = new ProductPresenter();
    int type = 0;
    MainActivity activity;

    public GetListMenuProductTask(MainActivity activity, int type) {
        this.activity = activity;
        this.type = type;
    }

    @Override
    protected List<Product> doInBackground(Integer... values) {
        List<Product> list = new ArrayList<>();
        type = values[0];
        if (values[0] == 0) {
            list = process.getTopNewProduct();
        }
        if (values[0] == 1) {
            list = process.getTopNikonProduct();
        }
        if (values[0] == 2) {
            list = process.getTopCanonProduct();
        }
        if (values[0] == 3) {
            list = process.getTopUnderTenMillion();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Product> list) {
        if (type == 0) {
            activity.loadNewProduct(list);
        }
        if (type == 1) {
            activity.loadNikonProduct(list);
        }
        if (type == 2) {
            activity.loadCanonProduct(list);
        }
        if (type == 3) {
            activity.loadListUnderTenMillion(list);
        }
        super.onPostExecute(list);
    }
}
