package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.AccontView.ChangePassActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class ChangePassTask extends AsyncTask<String, Integer, Void> {
    ChangePassActivity activity;
    AccountProcess process = new AccountProcess();
    ProgressDialog dialog;

    public ChangePassTask(ChangePassActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Đang cập nhật...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(String... values) {
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        int result = process.upPass(accountInformation.getId(), values[0], values[1]);
        publishProgress(result);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.result(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
