package com.example.phanv.camera.Model.ChatModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.ChatPresenter;
import com.example.phanv.camera.View.ChatView.ChatActivity;
import com.example.phanv.camera.View.Other.MainActivity;

/**
 * Created by phanv on 07-Dec-17.
 */

public class GetNodeChatTask extends AsyncTask<String, Void, Void> {
    ChatActivity activity;
    ChatPresenter process = new ChatPresenter();

    public GetNodeChatTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... values) {

        String idSend = MainActivity.idAccount;
        activity.idSend = idSend;
        String idReceive = values[0];
        String node = process.getNode(idSend, idReceive);
        activity.node = node;
        activity.listener();
        return null;
    }
}
