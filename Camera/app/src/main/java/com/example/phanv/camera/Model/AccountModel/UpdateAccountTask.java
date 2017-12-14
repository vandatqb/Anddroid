package com.example.phanv.camera.Model.AccountModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Presenter.AccountPresenter;
import com.example.phanv.camera.View.AccontView.EditAccountActivity;
import com.example.phanv.camera.View.Other.MainActivity;

/**
 * Created by phanv on 06-Dec-17.
 */

public class UpdateAccountTask extends AsyncTask<Account, Void, Void> {
    AccountPresenter process = new AccountPresenter();
    EditAccountActivity activity;
    DataLocalProcess processData;
    ProgressDialog dialog;

    public UpdateAccountTask(EditAccountActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        processData= new DataLocalProcess(activity);
    }

    @Override
    protected Void doInBackground(Account... account) {
        DataLocalProcess data = new DataLocalProcess(activity);
        AccountInformation accountInformation = data.read();
        int result = process.updateInfor(accountInformation.getId(), account[0].getFullName(), account[0].getPhone(), account[0].getAddress(), account[0].getEmail(), account[0].getImage());
        if (result > 0) {
            activity.updatSuccess();
            processData.write(account[0].getIdAccount(),account[0].getLoginName(),account[0].getFullName(),account[0].getImage());

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
