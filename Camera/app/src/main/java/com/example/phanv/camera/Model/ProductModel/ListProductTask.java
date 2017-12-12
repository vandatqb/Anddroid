package com.example.phanv.camera.Model.ProductModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.ProductView.ProductFragment;

import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class ListProductTask extends AsyncTask<Void, List<Product>, List<Product>> {
    ProductProcess process = new ProductProcess();
    ProductFragment fragment;
    ProgressDialog dialog;

    public ListProductTask(ProductFragment fragment) {
        this.fragment = fragment;
        dialog = new ProgressDialog(fragment.getActivity());
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        LocalDataProcess data = new LocalDataProcess(fragment.getActivity());
        AccountInformation accountInformation = data.read();
        List<Product> list = process.getAllProductWithId(accountInformation.getId());
        publishProgress(list);
        return null;
    }

    @Override
    protected void onProgressUpdate(List<Product>... values) {
        fragment.loadData(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Product> list) {
        dialog.dismiss();
        super.onPostExecute(list);
    }
}
