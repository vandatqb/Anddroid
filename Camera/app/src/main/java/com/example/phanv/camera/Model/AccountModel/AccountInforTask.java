package com.example.phanv.camera.Model.AccountModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.AccountPresenter;
import com.example.phanv.camera.View.Other.MainActivity;
import com.example.phanv.camera.View.ProductView.ViewDetailAccountActivity;
import com.example.phanv.camera.View.ProductView.ViewProductActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class AccountInforTask extends AsyncTask<String, Account, Account> {
    AccountPresenter process = new AccountPresenter();
    AccountInformationInterface mInterface;
    Activity activity;
    ProgressDialog dialog;

    public AccountInforTask(AccountInformationInterface mInterface, Activity activity) {
        this.activity = activity;
        this.mInterface = mInterface;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected Account doInBackground(String... idAccount) {
        Account account;
        if (activity instanceof ViewProductActivity || activity instanceof ViewDetailAccountActivity) {
            account = process.getAccountInformation(idAccount[0]);
        } else {
            account = process.getAccountInformation(MainActivity.idAccount);
            publishProgress(account);
        }
        return account;
    }

    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        mInterface.loadSuccess(account);
        dialog.dismiss();
    }
}
