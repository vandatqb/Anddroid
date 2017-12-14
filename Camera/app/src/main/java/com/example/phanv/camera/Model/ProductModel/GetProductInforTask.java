package com.example.phanv.camera.Model.ProductModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.ProductView.EditProductActivity;
import com.example.phanv.camera.View.ProductView.EditProductInterface;

/**
 * Created by phanv on 08-Dec-17.
 */

public class GetProductInforTask extends AsyncTask<String, Product, Product> {
    Activity activity;
    ProgressDialog dialog;

    EditProductInterface mInterface;

    public GetProductInforTask(Activity activity, EditProductInterface editProductInterface) {
        this.activity = activity;
        this.mInterface = editProductInterface;
        dialog = new ProgressDialog(activity);
    }

    ProductPresenter process = new ProductPresenter();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    protected Product doInBackground(String... strings) {
        Product product = new Product();
        try {
            product = process.getProductInfor(strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        publishProgress(product);
        return product;
    }

    @Override
    protected void onPostExecute(Product values) {
        dialog.dismiss();

        if (activity instanceof EditProductActivity) {
            mInterface.loadSuccess(values);

        } else {
            mInterface.loadSuccess(values);
        }
        super.onPostExecute(values);
    }
}
