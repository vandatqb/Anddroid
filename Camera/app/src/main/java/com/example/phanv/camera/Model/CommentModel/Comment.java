package com.example.phanv.camera.Model.CommentModel;

/**
 * Created by phanv on 14-Dec-17.
 */

public class Comment {
    private String idAccount;
    private String getIdAccountComment;
    private String content;
    private String time;
    private String name;
    private String img;
    private String start;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Comment(String idAccount, String getIdAccountComment, String content, String time, String name, String img, String start) {
        this.idAccount = idAccount;
        this.getIdAccountComment = getIdAccountComment;
        this.content = content;
        this.time = time;
        this.name = name;
        this.img = img;
        this.start = start;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getGetIdAccountComment() {
        return getIdAccountComment;
    }

    public void setGetIdAccountComment(String getIdAccountComment) {
        this.getIdAccountComment = getIdAccountComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
