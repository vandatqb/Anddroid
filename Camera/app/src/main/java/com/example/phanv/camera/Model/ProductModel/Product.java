package com.example.phanv.camera.Model.ProductModel;

/**
 * Created by phanv on 30-Nov-17.
 */

public class Product {
    private String id;
    private String idAccount;
    private String name;
    private String idMaker;
    private String nameMaker;
    private String type;
    private String mega;
    private String video;
    private String nameVideo;
    private String addition;
    private String price;
    private String time;
    private String status;
    private String image;
    private String image1;
    private String image2;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdMaker() {
        return idMaker;
    }

    public void setIdMaker(String idMaker) {
        this.idMaker = idMaker;
    }

    public String getNameMaker() {
        return nameMaker;
    }

    public void setNameMaker(String nameMaker) {
        this.nameMaker = nameMaker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMega() {
        return mega;
    }

    public void setMega(String mega) {
        this.mega = mega;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product(String id, String idAccount, String name, String idMaker, String nameMaker, String type, String mega, String video, String nameVideo, String addition, String price, String time, String status, String image, String image1, String image2,String description) {
        this.id = id;
        this.idAccount = idAccount;
        this.name = name;
        this.idMaker = idMaker;
        this.nameMaker = nameMaker;
        this.type = type;
        this.mega = mega;
        this.video = video;
        this.nameVideo = nameVideo;
        this.addition = addition;
        this.price = price;
        this.time = time;
        this.status = status;
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.description =description;
    }
}
