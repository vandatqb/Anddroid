package com.example.phanv.camera.Model.ProductModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.Other.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 07-Dec-17.
 */

public class GetListMakerTask extends AsyncTask<Void, List<Maker>, Void> {
    MainActivity activity;
    DataLocalProcess localProcess;
    ProgressDialog dialog;
    ProductPresenter process = new ProductPresenter();

    public GetListMakerTask(MainActivity activity) {
        this.activity = activity;
        localProcess = new DataLocalProcess(activity);
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<Maker> list = new ArrayList<>();
        list = process.getListMaker();
        List<Maker> listVideo = new ArrayList<>();
        listVideo = process.getListVideo();
        localProcess.writeListMaker(list);
        localProcess.writeListVideo(listVideo);
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
