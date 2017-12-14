package com.example.phanv.camera.Model.ChatModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ChatPresenter;
import com.example.phanv.camera.View.ChatView.ListChatActivity;
import com.example.phanv.camera.View.Other.MainActivity;

import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class GetListChatTask extends AsyncTask<Void, List<ListCameraChat>, Void> {
    ListChatActivity activity;
    ProgressDialog dialog;
    ChatPresenter process = new ChatPresenter();

    public GetListChatTask(ListChatActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<ListCameraChat> list = process.getListChat(MainActivity.idAccount);
        publishProgress(list);

        return null;
    }

    @Override
    protected void onProgressUpdate(List<ListCameraChat>[] values) {
        super.onProgressUpdate(values);
        activity.loadData(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
