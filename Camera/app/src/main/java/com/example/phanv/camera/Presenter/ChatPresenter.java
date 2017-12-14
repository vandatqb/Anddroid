package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.ChatModel.Chat;
import com.example.phanv.camera.Model.ChatModel.ChatProcess;
import com.example.phanv.camera.Model.ChatModel.ListCameraChat;

import java.util.List;

/**
 * Created by phanv on 13-Dec-17.
 */

public class ChatPresenter {
    public ChatProcess process = new ChatProcess();

    public List<ListCameraChat> getListChat(String idAccount) {
        return process.getListChat(idAccount);
    }

    public String getNode(String idSend, String idReceive) {
        return process.getNode(idSend, idReceive);
    }

    public List<Chat> getChatDetail(String idSend, String idReceive) {
        return process.getChatDetail(idSend, idReceive);
    }

    public boolean addChat(String idSend, String idReceive, String content) {
        return process.addChat(idSend, idReceive, content);
    }
}
