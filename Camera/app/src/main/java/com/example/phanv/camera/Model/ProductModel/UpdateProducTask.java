package com.example.phanv.camera.Model.ProductModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.View.ProductView.EditProductInterface;

/**
 * Created by phanv on 08-Dec-17.
 */

public class UpdateProducTask extends AsyncTask<Product,Void , Void> {
    ProductProcess process=new ProductProcess();
    Activity activity;
    ProgressDialog dialog;
    EditProductInterface anInterface;
    public UpdateProducTask(Activity activity,EditProductInterface anInterface){
        this.activity=activity;
        dialog=new ProgressDialog(activity);
        this.anInterface=anInterface;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        anInterface.EditSuccess();
    }

    @Override
    protected Void doInBackground(Product... products) {
        process.updateProduct(products[0]);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading...");
        dialog.show();
    }
}
