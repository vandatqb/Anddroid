package com.example.phanv.camera.Model.CommentModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.CommentPresenter;

import java.util.List;

/**
 * Created by phanv on 14-Dec-17.
 */

public class GetListCommentTask extends AsyncTask<String,Void,List<Comment>> {
    CommentPresenter presenter = new CommentPresenter();
    CommentInterface anInterface;
    public GetListCommentTask(CommentInterface anInterface){
        this.anInterface=anInterface;
    }

    @Override
    protected List<Comment> doInBackground(String... values) {
        List<Comment> list=presenter.getListComment(values[0]);
        return list;
    }

    @Override
    protected void onPostExecute(List<Comment> comments) {
        anInterface.getListCommentSuccess(comments);
        super.onPostExecute(comments);
    }
}
