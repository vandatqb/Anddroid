package com.example.phanv.camera.View.ChatView;

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

import com.example.phanv.camera.Model.ChatModel.AddChatTask;
import com.example.phanv.camera.Model.ChatModel.Chat;
import com.example.phanv.camera.Model.ChatModel.GetChatDetailTask;
import com.example.phanv.camera.Model.ChatModel.GetNodeChatTask;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.Other.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public   String node;
    private LinearLayout layout;
    private RelativeLayout layout_2;
    private ImageView sendButton;
    private EditText messageArea;
    private ScrollView scrollView;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private  String idSend;
    private  String idReceive;
    private GetNodeChatTask task;
    private GetChatDetailTask taskChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        idSend=MainActivity.idAccount;

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id");
        idReceive = bundle.getString("id");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("chat");
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        task = new GetNodeChatTask(this);
        task.execute(idReceive);
        taskChat = new GetChatDetailTask(this);
        taskChat.execute(idReceive);
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

    public void listener() {
        if (node.length() > 3 & node != null) {
            reference.child(node).child(idReceive).child("chat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat != null) {

                        addMessageBox(chat.getContent(), 2, chat.getTime());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void loadChatDetail(List<Chat> list) {
        for (Chat chat : list) {
            String content = chat.getContent();
            String idSend2 = chat.getIdSend();
            int type = idSend2.equals(idSend) ? 1 : 0;
            String time = chat.getTime();
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
        AddChatTask task = new AddChatTask();
        task.execute(idSend, idReceive, message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
