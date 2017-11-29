package com.example.phanv.virus.Model;

/**
 * Created by phanv on 27-Oct-17.
 */

public class CallLogVirus {
    public String date;
    public String time;
    public String phone;
    public String type;

    public void setDate(String date) {
        this.date = date;
    }

    public String duration;


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CallLogVirus(String date, String time, String phone, String type, String duration) {
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.type = type;
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
