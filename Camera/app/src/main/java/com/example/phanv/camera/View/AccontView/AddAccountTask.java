package com.example.phanv.camera.View.AccontView;

import android.os.AsyncTask;

import com.example.phanv.camera.Model.AccountModel.RegisterInterface;
import com.example.phanv.camera.Presenter.AccountPresenter;

/**
 * Created by phanv on 14-Dec-17.
 */

public class AddAccountTask extends AsyncTask<String, Integer, Integer> {
    RegisterInterface anInterface;
    AccountPresenter presenter = new AccountPresenter();

    public AddAccountTask(RegisterInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    protected Integer doInBackground(String... value) {
        Integer result = presenter.register(value[0], value[1], value[2], value[3], value[4], value[5], value[6]);
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        anInterface.loginSucces(integer);
    }
}
