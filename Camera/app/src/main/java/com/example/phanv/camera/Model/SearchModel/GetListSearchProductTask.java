package com.example.phanv.camera.Model.SearchModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Presenter.SearchPresenter;
import com.example.phanv.camera.View.SearchView.SearchInterface;

import java.util.List;

/**
 * Created by phanv on 15-Dec-17.
 */

public class GetListSearchProductTask extends AsyncTask<String, List<Product>, List<Product>> {
    Activity activity;
    SearchInterface anInterface;
    ProgressDialog dialog;
    SearchPresenter presenter = new SearchPresenter();

    public GetListSearchProductTask(SearchInterface searchInterface, Activity activity) {
        this.anInterface = searchInterface;
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading....");
        dialog.show();
    }

    @Override
    protected List<Product> doInBackground(String... values) {
        Integer number = Integer.parseInt(values[0]);
        return presenter.getListSearchProduct(number, values[1]);
    }

    @Override
    protected void onPostExecute(List<Product> list) {
        super.onPostExecute(list);
        anInterface.getListSearchSuccess(list);
        dialog.dismiss();
    }
}
