package com.example.phanv.camera.Model.ChatModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.ChatView.ChatActivity;

/**
 * Created by phanv on 07-Dec-17.
 */

public class GetNodeChatTask extends AsyncTask<String, Void, Void> {
    ChatActivity activity;
    ChatProcess process = new ChatProcess();

    public GetNodeChatTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... values) {
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        String idSend = accountInformation.getId();
        activity.idSend = idSend;
        String idReceive = values[0];
        String node = process.getNode(idSend, idReceive);
        activity.node = node;
        activity.listener();
        return null;
    }
}
