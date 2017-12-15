package com.example.phanv.camera.Model.AccountModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.AccountPresenter;

/**
 * Created by phanv on 14-Dec-17.
 */

public class CheckLoginNameTask extends AsyncTask<String,Void,Boolean> {
    RegisterInterface anInterface;
    AccountPresenter presenter= new AccountPresenter();
    public CheckLoginNameTask(RegisterInterface registerInterface){
        this.anInterface=registerInterface;
    }
    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean result=presenter.checkLoginName(strings[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        anInterface.checkLogin(aBoolean);
    }
}
