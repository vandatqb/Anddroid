package com.example.phanv.camera.Model.CommentModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.CommentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 14-Dec-17.
 */

public class GetStartCommentTask extends AsyncTask<String, Void, List<Float>> {
    CommentInterface anInterface;
    CommentPresenter presenter = new CommentPresenter();

    public GetStartCommentTask(CommentInterface anInterface) {
        this.anInterface = anInterface;
    }
    @Override
    protected List<Float> doInBackground(String... values) {
        List<Float> list = new ArrayList<>();
        float count = presenter.getCountComment(values[0]);
        float avg = presenter.getAvgStart(values[0]);
        list.add(count);
        list.add(avg);
        return list;
    }

    @Override
    protected void onPostExecute(List<Float> floats) {
        super.onPostExecute(floats);
        anInterface.getStartComment(floats.get(0), floats.get(1));
    }
}
