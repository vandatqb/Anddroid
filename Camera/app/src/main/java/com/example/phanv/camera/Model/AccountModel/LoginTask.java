package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.AccontView.LoginFragment;

/**
 * Created by phanv on 07-Dec-17.
 */

public class LoginTask extends AsyncTask<String, Integer, Integer> {
    LoginFragment fragment;
    AccountProcess process = new AccountProcess();
    ProgressDialog dialog;

    public LoginTask(LoginFragment fragment) {
        this.fragment = fragment;
        dialog = new ProgressDialog(fragment.getActivity());
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... values) {
        int result = 0;
        result = process.checkLogin(values[0], values[1]);
        if (result > 0) {
            LocalDataProcess process = new LocalDataProcess(fragment.getActivity());
            process.writeId(result + "");
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer value) {
        fragment.checkLogin(value);
        dialog.dismiss();
    }
}
