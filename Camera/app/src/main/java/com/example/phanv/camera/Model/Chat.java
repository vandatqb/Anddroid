package com.example.phanv.camera.Model;

/**
 * Created by phanv on 16-Nov-17.
 */

public class Chat {
    private String idSend;
    private String idReceive;
    private String time;
    private String content;
    public Chat()
    {

    }
    public Chat(String idSend, String idReceive, String time, String content) {
        this.idSend = idSend;
        this.idReceive = idReceive;
        this.time = time;
        this.content = content;
    }

    public String getIdSend() {
        return idSend;
    }

    public void setIdSend(String idSend) {
        this.idSend = idSend;
    }

    public String getIdReceive() {
        return idReceive;
    }

    public void setIdReceive(String idReceive) {
        this.idReceive = idReceive;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
