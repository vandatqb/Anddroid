package com.example.phanv.camera.Model.ProductModel;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.phanv.camera.Presenter.ProductPresenter;
import com.example.phanv.camera.View.Other.MainActivity;

/**
 * Created by phanv on 13-Dec-17.
 */

public class FavouriteTask extends AsyncTask<String, Void, Void> {
    Activity activity;

    public FavouriteTask(Activity activity) {
        this.activity = activity;
    }

    ProductPresenter process = new ProductPresenter();

    @Override
    protected Void doInBackground(String... strings) {
        process.addOrRemoveFavourite(MainActivity.idAccount, strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(activity, "Đã cập nhật danh sách yêu thích cả bạn", Toast.LENGTH_SHORT).show();
    }
}
