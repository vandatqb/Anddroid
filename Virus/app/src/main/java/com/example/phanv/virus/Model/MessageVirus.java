package com.example.phanv.virus.Model;

import java.io.Serializable;

/**
 * Created by phanv on 27-Oct-17.
 */
// implements Serializable
public class MessageVirus{
    private String smsDate;
    private String type;
    private String time;
    private String address;
    private String content;

    public MessageVirus(String smsDate, String type, String time, String addres, String content) {
        this.smsDate = smsDate;
        this.type = type;
        this.time = time;
        this.address = addres;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddres(String addres) {
        this.address = addres;
    }

    public String getType() {
        return type;
    }

    public String getAddres() {
        return address;
    }

    public String getSmsDate() {
        return smsDate;
    }

    public String getContent() {
        return content;
    }

    public void setSmsDate(String smsDate) {
        this.smsDate = smsDate;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
