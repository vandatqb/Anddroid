package com.example.phanv.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phanv.service.Service.BoundServiceBinder;
import com.example.phanv.service.Service.BoundServiceMessage;
import com.example.phanv.service.Service.MyIntentService;
import com.example.phanv.service.Service.UnBoundService;

public class MainActivity extends Activity implements View.OnClickListener {
    Button btStart;
    Button btStop;
    Button btPlay;
    Button btDestroy;
    BoundServiceBinder mService;
    boolean bound =false;
    Messenger messengerService = null;
    boolean boundMessage =false;
    Button btStartMessage;
    Button btStopMessage;
    Button btStartIntentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIdAndEvent();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!bound)
        {
            Intent intent = new Intent(this, BoundServiceBinder.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        if (!boundMessage)
        {
            Intent intent = new Intent(this, BoundServiceMessage.class);
            bindService(intent, messageConnection, Context.BIND_AUTO_CREATE);
        }
    }
    private void setIdAndEvent() {
        btStart = (Button) findViewById(R.id.btStart);
        btStop = (Button) findViewById(R.id.btStop);
        btPlay = (Button) findViewById(R.id.btPlay);
        btDestroy = (Button) findViewById(R.id.btDestroy);
        btStopMessage = (Button) findViewById(R.id.btStopMessage);
        btStartMessage = (Button) findViewById(R.id.btStartMessage);
        btStartIntentService = (Button) findViewById(R.id.btStartIntent);
        btPlay.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btStart.setOnClickListener(this);
        btDestroy.setOnClickListener(this);
        btStartMessage.setOnClickListener(this);
        btStopMessage.setOnClickListener(this);
        btStartIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btStart){
            startService(new Intent(getApplicationContext(), UnBoundService.class));
        }
        if (view == btStop){
            stopService(new Intent(getApplicationContext(), UnBoundService.class));
        }
        if (view == btPlay )
        {
                Toast.makeText(this,"b :"+bound,Toast.LENGTH_SHORT).show();
                if(bound)
                {
                    mService.play();
                }
        }
        if (view == btDestroy)
        {
            if (bound)
            {
                mService.pause();
            }
        }
        if (view == btStartMessage)
        {
            if (boundMessage)
            {
                Message message = Message.obtain(null,BoundServiceMessage.MSG_SAY_HELLO,0,0);
                try
                {
                    messengerService.send(message);
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if (view == btStopMessage)
        {
            unbindService(messageConnection);
            boundMessage = false;
        }
        if (view==btStartIntentService)
        {
            startService(new Intent(getApplicationContext(), MyIntentService.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
        if (boundMessage)
        {
            unbindService(messageConnection);
            boundMessage = false;
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
            BoundServiceBinder.LocalBinder binder = (BoundServiceBinder.LocalBinder) service;
            mService = binder.getService();
            bound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MainActivity.this,"Not Connected",Toast.LENGTH_SHORT).show();
            bound = false;
        }
    };
    private ServiceConnection messageConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            messengerService = new Messenger(service);
            boundMessage = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            boundMessage = false;
        }
    };
}
