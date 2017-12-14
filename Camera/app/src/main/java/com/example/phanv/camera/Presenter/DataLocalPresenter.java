package com.example.phanv.camera.Presenter;

import android.app.Activity;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Model.ProductModel.Maker;

import java.util.List;

/**
 * Created by phanv on 13-Dec-17.
 */

public class DataLocalPresenter {
    public Activity activity;

    public DataLocalPresenter(Activity activity) {
        this.activity = activity;
    }

    public DataLocalProcess process = new DataLocalProcess(activity);

    public AccountInformation read() {
        return process.read();
    }

    public void write(String id, String loginName, String fullName, String image) {
        process.write(id, loginName, fullName, image);
    }

    public void writeId(String id) {
        process.writeId(id);
    }

    public void writeListMaker(List<Maker> list) {
        process.writeListMaker(list);
    }

    public List<Maker> readListMaker() {
        return process.readListMaker();
    }

    public void writeListVideo(List<Maker> list) {
        process.writeListVideo(list);
    }

    public List<Maker> readListVideo() {
        return process.readListVideo();
    }

}
