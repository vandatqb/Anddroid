package com.example.phanv.camera.Model.SearchModel;

import android.os.AsyncTask;

import com.example.phanv.camera.Presenter.SearchPresenter;
import com.example.phanv.camera.View.SearchView.SearchInterface;

/**
 * Created by phanv on 15-Dec-17.
 */

public class GetCountSearchTask extends AsyncTask<String,Void,Integer> {
    SearchPresenter presenter = new SearchPresenter();
    SearchInterface anInterface;
    public GetCountSearchTask(SearchInterface anInterface)
    {
        this.anInterface=anInterface;
    }
    @Override
    protected Integer doInBackground(String... values) {
        return presenter.getCountSearch(values[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        anInterface.getCountSearchSuccess(integer);

    }
}
