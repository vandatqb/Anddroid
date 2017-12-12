package com.example.phanv.camera.View.ProductView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.ProductModel.ProductProcess;

/**
 * Created by phanv on 08-Dec-17.
 */

public class DeleteProductTask extends AsyncTask<String, Void, Void> {
    Activity activity;
    ProductProcess process = new ProductProcess();
    ProgressDialog dialog;

    public DeleteProductTask(Activity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    protected Void doInBackground(String... strings) {
        process.deleteProduct(strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        Intent intent = new Intent(activity, ProductActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
