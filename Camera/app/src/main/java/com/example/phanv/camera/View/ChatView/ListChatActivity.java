package com.example.phanv.camera.View.ChatView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.phanv.camera.Model.ChatModel.GetListChatTask;
import com.example.phanv.camera.Model.ChatModel.ListCameraChat;
import com.example.phanv.camera.R;

import java.util.List;

public class ListChatActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private ListChatAdapter mAdapter;
    private SearchView svChat;
    private GetListChatTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        mRecyclerView = findViewById(R.id.rcvListChat);
        svChat = findViewById(R.id.searchListChat);
        svChat.setOnQueryTextListener(this);
        task = new GetListChatTask(this);
        task.execute();
    }

    public void loadData(List<ListCameraChat> list) {
        mAdapter = new ListChatAdapter(list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
