package com.example.phanv.camera.Model.ChatModel;

import android.os.AsyncTask;

/**
 * Created by phanv on 07-Dec-17.
 */

public class AddChatTask extends AsyncTask<String, Void, Void> {
    ChatProcess process = new ChatProcess();

    @Override
    protected Void doInBackground(String... values) {
        process.addChat(values[0], values[1], values[2]);
        return null;
    }
}
