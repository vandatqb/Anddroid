package com.example.phanv.camera.Model.ProductModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.Other.MainActivity;
import com.example.phanv.camera.View.ProductView.AddProductActivity;

/**
 * Created by phanv on 07-Dec-17.
 */

public class AddProductTask extends AsyncTask<Product, Boolean, Void> {
    AddProductActivity activity;
    ProductPresenter process = new ProductPresenter();
    ProgressDialog dialog;

    public AddProductTask(AddProductActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Product... values) {
        Boolean result = process.addProduct(values[0], MainActivity.idAccount);
        publishProgress(result);
        return null;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
        activity.success = values[0];
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        activity.addSuccess();
    }
}
