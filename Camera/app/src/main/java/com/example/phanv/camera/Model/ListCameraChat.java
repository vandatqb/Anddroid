package com.example.phanv.camera.Model;

/**
 * Created by phanv on 27-Nov-17.
 */

public class ListCameraChat {
    private String img;
    private String name;
    private String other;

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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public ListCameraChat(String img, String name, String other, String id) {
        this.img = img;
        this.name = name;
        this.other = other;
        this.id = id;
    }
}
