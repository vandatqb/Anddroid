package com.example.phanv.camera.Model.ProductModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.Other.MainActivity;
import com.example.phanv.camera.View.ProductView.GetListProductInterface;
import com.example.phanv.camera.View.ProductView.ViewDetailAccountActivity;

import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class ListProductTask extends AsyncTask<String, List<Product>, List<Product>> {
    ProductPresenter process = new ProductPresenter();
    GetListProductInterface mInterface;
    Activity activity;
    ProgressDialog dialog;

    public ListProductTask(GetListProductInterface mInterface, Activity activity) {
        this.mInterface = mInterface;
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected List<Product> doInBackground(String... values) {
        List<Product> list;
        if (activity instanceof ViewDetailAccountActivity) {
            list = process.getAllProductWithId(values[0]);
        } else {
            if (values[0].equals("favorite")) {
                list = process.getFavouriteProduct(MainActivity.idAccount);
            } else {
                list = process.getAllProductWithId(MainActivity.idAccount);
            }

        }

        return list;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Product> list) {
        mInterface.loadSuccess(list);
        dialog.dismiss();
        super.onPostExecute(list);
    }
}
