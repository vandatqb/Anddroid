package com.example.phanv.camera.Model.ChatModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.View.ChatView.ChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 06-Dec-17.
 */

public class GetChatDetailTask extends AsyncTask<String, List<Chat>, List<Chat>> {
    ChatActivity activity;
    ChatProcess process = new ChatProcess();

    public GetChatDetailTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Chat> doInBackground(String... values) {
        List<Chat> list = new ArrayList<>();
        LocalDataProcess data = new LocalDataProcess(activity);
        AccountInformation accountInformation = data.read();
        list = process.getChatDetail(accountInformation.getId(), values[0]);
        publishProgress(list);
        return null;
    }

    @Override
    protected void onProgressUpdate(List<Chat>[] values) {
        activity.loadChatDetail(values[0]);
        super.onProgressUpdate(values);
    }
}
