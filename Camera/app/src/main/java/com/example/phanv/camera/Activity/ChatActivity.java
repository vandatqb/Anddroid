package com.example.phanv.camera.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.camera.Model.Chat;
import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Model.Property;
import com.example.phanv.camera.Process.LocalData;
import com.example.phanv.camera.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    FirebaseDatabase database;
    DatabaseReference reference;
    String node;
    String idSend;
    String idReceive;
    ConnectServer connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LocalData data=new LocalData(this);
        Local local= data.read();
        idSend=local.getId();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id");
        idReceive = bundle.getString("id");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("chat");
        connect = new ConnectServer();
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        getNode();
        getChatDetail();
        Toast.makeText(this,idSend+"-"+idReceive,Toast.LENGTH_SHORT).show();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageArea.getText().toString();
                if (message.length() > 0) {
                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss").format(Calendar.getInstance().getTime());
                    Chat chat = new Chat(idSend, idReceive, timeStamp, message);
                    addMessageBox(message, 1, timeStamp);
                    upMessage(message);
                    messageArea.setText("");
                    reference.child(node).child(idSend).child("chat").setValue(chat);
                }
            }
        });
    }

    private void getNode() {
        List<Property> list = new ArrayList<>();
        Property idSend1 = new Property("idsend",idSend);
        Property idRec = new Property("idreceive", idReceive);
        list.add(idSend1);
        list.add(idRec);
        String address = "http://tempuri.org/getNode";
        String action = "getNode";
        node = connect.processString(list, address, action);
        if (!node.equals("0")) {
            reference.child(node).child(idReceive).child("chat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat!=null){
                        addMessageBox(chat.getContent(),2,chat.getTime());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void getChatDetail() {
        List<Property> list = new ArrayList<>();
        Property idSend1 = new Property("idsend", idSend);
        Property idRec = new Property("idreceive", idReceive);
        list.add(idSend1);
        list.add(idRec);
        String address = "http://tempuri.org/getChatDetail";
        String action = "getChatDetail";
        SoapObject object = connect.process(list, address, action);
        for (int i = 0; i < object.getPropertyCount(); i++) {
            SoapObject chat = (SoapObject) object.getProperty(i);
            String content = chat.getProperty("Content").toString();
            String idSend2 = chat.getProperty("Id_send").toString();
            int type = idSend2.equals(idReceive) ? 1 : 0;
            String time = chat.getProperty("Time").toString();
            addMessageBox(content, type, time);
        }
    }

    public void addMessageBox(String message, int type, final String time) {
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, time, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setTextColor(Color.BLUE);
            textView.setTextSize(22);
        } else {
            lp2.gravity = Gravity.LEFT;
            textView.setTextSize(22);
            textView.setTextColor(Color.BLACK);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void upMessage(String message) {
        List<Property> list = new ArrayList<>();
        Property idSend1 = new Property("idsend", idSend);
        Property idRec = new Property("idreceive", idReceive);
        Property content = new Property("content", message);
        list.add(idSend1);
        list.add(idRec);
        list.add(content);
        String address = "http://tempuri.org/addChat";
        String action = "addChat";
        SoapObject object = connect.process(list, address, action);
    }
}
