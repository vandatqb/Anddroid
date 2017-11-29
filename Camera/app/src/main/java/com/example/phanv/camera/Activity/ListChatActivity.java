package com.example.phanv.camera.Activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.ListCameraChat;
import com.example.phanv.camera.Model.ListChatAdapter;
import com.example.phanv.camera.Model.Property;
import com.example.phanv.camera.R;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class ListChatActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView mRecyclerView;
    ListChatAdapter mAdapter;
    List<ListCameraChat> mList;
    ConnectServer connect;
    SearchView svChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mList = new ArrayList<>();
        connect = new ConnectServer();
        mRecyclerView = findViewById(R.id.rcvListChat);
        svChat = findViewById(R.id.searchListChat);
        svChat.setOnQueryTextListener(this);
        getData();
        mAdapter = new ListChatAdapter(mList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        List<Property> list = new ArrayList<>();
        Property pr = new Property("id", "11");
        list.add(pr);
        String soapAddress = "http://tempuri.org/getListChat";
        String action = "getListChat";
        SoapObject object = connect.process(list, soapAddress, action);
        for (int i = 0; i < object.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) object.getProperty(i);
            String image = obj.getProperty("Image").toString();
            String name = obj.getProperty("Full_name").toString();
            String id = obj.getProperty("Id").toString();
            ListCameraChat detail = new ListCameraChat(image, name, "s", id);
            mList.add(detail);
        }
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
}
