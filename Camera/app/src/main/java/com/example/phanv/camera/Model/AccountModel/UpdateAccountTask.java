package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.AccontView.EditAccountActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class UpdateAccountTask extends AsyncTask<Account, Void, Void> {
    AccountProcess process = new AccountProcess();
    EditAccountActivity activity;
    ProgressDialog dialog;

    public UpdateAccountTask(EditAccountActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected Void doInBackground(Account... account) {
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        int result = process.updateInfor(accountInformation.getId(), account[0].getFullName(), account[0].getPhone(), account[0].getAddress(), account[0].getEmail(), account[0].getImage());
        if (result > 0) {
            activity.updatSuccess();
        } else {
            Toast.makeText(activity, "Đã có lỗi xảy ra", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Đang cập nhật");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
