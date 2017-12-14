package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Presenter.AccountPresenter;
import com.example.phanv.camera.View.AccontView.LoginFragment;

/**
 * Created by phanv on 07-Dec-17.
 */

public class LoginTask extends AsyncTask<String, Integer, Integer> {
    LoginFragment fragment;
    AccountPresenter process = new AccountPresenter();
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
            DataLocalProcess process = new DataLocalProcess(fragment.getActivity());
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
