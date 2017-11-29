package com.example.phanv.camera.Model;

/**
 * Created by phanv on 28-Nov-17.
 */

public class Local {
    private String id;
    private String loginName;
    private String fullName;
    private String imgae;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImgae() {
        return imgae;
    }

    public void setImgae(String imgae) {
        this.imgae = imgae;
    }

    public Local(String id, String loginName, String fullName, String imgae) {
        this.id = id;
        this.loginName = loginName;
        this.fullName = fullName;
        this.imgae = imgae;
    }
}
