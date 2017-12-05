package com.example.phanv.camera.Model.ProductModel;

/**
 * Created by phanv on 04-Dec-17.
 */

public class Maker {
    private String idMaker;
    private String nameMaker;

    public Maker(String idMaker, String nameMaker) {
        this.idMaker = idMaker;
        this.nameMaker = nameMaker;
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
}
