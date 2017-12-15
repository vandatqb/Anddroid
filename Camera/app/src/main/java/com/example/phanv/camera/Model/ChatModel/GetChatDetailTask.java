package com.example.phanv.camera.Model.ChatModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ChatPresenter;
import com.example.phanv.camera.View.ChatView.ChatActivity;
import com.example.phanv.camera.View.Other.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class GetChatDetailTask extends AsyncTask<String, List<Chat>, List<Chat>> {
    ChatActivity activity;
    ChatPresenter process = new ChatPresenter();
    ProgressDialog dialog;

    public GetChatDetailTask(ChatActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    public List<Chat> doInBackground(String... values) {
        List<Chat> list = new ArrayList<>();
        list = process.getChatDetail(MainActivity.idAccount, values[0]);
        publishProgress(list);
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    public void onProgressUpdate(List<Chat>[] values) {
        activity.loadChatDetail(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Chat> chats) {
        super.onPostExecute(chats);
        dialog.dismiss();
    }
}
