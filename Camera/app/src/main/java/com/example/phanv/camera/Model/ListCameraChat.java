package com.example.phanv.camera.Model;

/**
 * Created by phanv on 27-Nov-17.
 */

public class ListCameraChat {
    private String img;
    private String name;
    private String content;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListCameraChat(String img, String name, String content, String time, String id) {
        this.img = img;
        this.name = name;
        this.content = content;
        this.time = time;
        this.id = id;
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
