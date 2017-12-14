package com.example.phanv.camera.Presenter;

import com.example.phanv.camera.Model.CommentModel.Comment;
import com.example.phanv.camera.Model.CommentModel.CommentProcess;

import java.util.List;

/**
 * Created by phanv on 14-Dec-17.
 */

public class CommentPresenter {
    CommentProcess process = new CommentProcess();
    public int addComment(String idAccount, String idComment, String content,String start) {
        return process.addComment(idAccount,idComment,content,start);
    }

    public List<Comment> getListComment(String idAccount) {
        return process.getListComment(idAccount);
    }

    public int getCountComment(String idAccount) {
        return process.getCountComment(idAccount);
    }

    public float getAvgStart(String idAccount) {
        return process.getAvgStart(idAccount);
    }

}
