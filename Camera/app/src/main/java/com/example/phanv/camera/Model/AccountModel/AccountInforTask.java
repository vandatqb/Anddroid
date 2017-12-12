package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.AccontView.AccountActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class AccountInforTask extends AsyncTask<Void, Account, Account> {
    AccountProcess process = new AccountProcess();
    AccountActivity activity;
    ProgressDialog dialog;

    public AccountInforTask(AccountActivity activity) {
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
    protected Account doInBackground(Void... idAccount) {
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        Account account = process.getAccountInformation(accountInformation.getId());
        publishProgress(account);
        return null;
    }

    @Override
    protected void onProgressUpdate(Account... values) {
        super.onProgressUpdate(values);
        activity.viewInformation(values[0]);
    }

    @Override
    protected void onPostExecute(Account aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
