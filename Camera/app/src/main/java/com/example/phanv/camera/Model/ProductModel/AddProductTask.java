package com.example.phanv.camera.Model.ProductModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.ProductView.AddProductActivity;

/**
 * Created by phanv on 07-Dec-17.
 */

public class AddProductTask extends AsyncTask<Product, Boolean, Void> {
    AddProductActivity activity;
    ProductProcess process = new ProductProcess();
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
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        Boolean result = process.addProduct(values[0], accountInformation.getId());
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
