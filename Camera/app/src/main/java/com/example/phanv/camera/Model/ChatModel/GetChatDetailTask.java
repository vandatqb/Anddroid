package com.example.phanv.camera.Model.ChatModel;

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

    public GetChatDetailTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    public List<Chat> doInBackground(String... values) {
        List<Chat> list = new ArrayList<>();
        list = process.getChatDetail(MainActivity.idAccount, values[0]);
        publishProgress(list);
        return null;
    }

    @Override
    public void onProgressUpdate(List<Chat>[] values) {
        activity.loadChatDetail(values[0]);
        super.onProgressUpdate(values);
    }
}
