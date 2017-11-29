package com.example.phanv.mydiary.Model;

/**
 * Created by phanv on 01-Nov-17.
 */

public class Diary {
    private int id;
    private String date;
    private String title;
    private String detail;
    private float like;
    private String url;

    public Diary(int id, String date, String title, String detail,float like,String url) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.detail = detail;
        this.like = like;
        this.url = url;
    }

    public Diary() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public float getLike() {
        return like;
    }

    public void setLike(float like) {
        this.like = like;
    }
}
