package com.example.phanv.mydiary.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.phanv.mydiary.Model.Diary;
import com.example.phanv.mydiary.Model.DiaryAdapter;
import com.example.phanv.mydiary.R;

import java.util.ArrayList;

public class ListDiaryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<Diary> list = new ArrayList<Diary>();
    RecyclerView recyclerView;
    DiaryAdapter diaryAdapter;
    DatabaseCreate db;
    Context context;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diary);
        context=this;
        db = new DatabaseCreate(context);
        recyclerView =  findViewById(R.id.rclView);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        setData();
    }
    private void setData() {
        String sql="select * from diary";
        list = db.getListDiary(sql);
        diaryAdapter = new DiaryAdapter(list,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(diaryAdapter);
        diaryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        diaryAdapter.getFilter().filter(newText);
        return true;
    }
}
