package com.example.phanv.virus.Model;

import java.io.Serializable;

/**
 * Created by phanv on 25-Oct-17.
 */
//implements Serializable
public class Position {
    private String dateTime;
    private String lat;
    private String lng;


    public Position(String dateTime, String lat, String lng) {
        this.dateTime = dateTime;
        this.lat = lat;
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
