package com.example.phanv.camera.Model.CommentModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.CommentPresenter;

/**
 * Created by phanv on 14-Dec-17.
 */

public class AddCommentTask extends AsyncTask <String,Void,Integer>{
    CommentPresenter presenter = new CommentPresenter();
    CommentInterface anInterface;
    public AddCommentTask(CommentInterface anInterface){
        this.anInterface=anInterface;
    }
    @Override
    protected Integer doInBackground(String... values) {
        int reuslt=presenter.addComment(values[0],values[1],values[2],values[3]);
        return reuslt;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        anInterface.addCommentSucces(integer);
    }
}
