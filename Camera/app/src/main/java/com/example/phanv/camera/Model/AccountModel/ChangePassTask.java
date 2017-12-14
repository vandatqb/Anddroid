package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.AccountPresenter;
import com.example.phanv.camera.View.AccontView.ChangePassActivity;
import com.example.phanv.camera.View.Other.MainActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class ChangePassTask extends AsyncTask<String, Integer, Integer> {
    ChangePassActivity activity;
    AccountPresenter process = new AccountPresenter();
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
    protected Integer doInBackground(String... values) {

        int result = process.upPass(MainActivity.idAccount, values[0], values[1]);
        publishProgress(result);
        return result;
    }

    @Override
    protected void onPostExecute(Integer values) {
        super.onPostExecute(values);
        activity.result(values);
        dialog.dismiss();
    }
}
