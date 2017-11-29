package com.example.phanv.mydiary.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.example.phanv.mydiary.Model.NotificationReceiver;
import com.example.phanv.mydiary.R;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    CheckBox cbSetting;
    TimePicker pickerTime;
    boolean check=false;
    int hour;
    int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setUp();
        getOldValue();
    }

    private void setUp() {
        cbSetting = (CheckBox) findViewById(R.id.checkBox);
        pickerTime = (TimePicker) findViewById(R.id.timePicker);
        cbSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotification();
            }
        });
        pickerTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                setNotification();
            }
        });
    }

    private void setValue() {
        SharedPreferences pre=getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor edit=pre.edit();
        edit.putBoolean("checked", check);
        edit.putInt("hour", hour);
        edit.putInt("minute", minute);
        edit.commit();
    }

    private void getOldValue() {
        SharedPreferences pre=getSharedPreferences
                ("data",MODE_PRIVATE);
        check = pre.getBoolean("checked", false);
        hour = pre.getInt("hour",-1);
        minute = pre.getInt("minute",-1);
        if (check ==true)
            cbSetting.setChecked(true);
        else
            cbSetting.setChecked(false);
        if (hour!=-1& minute!=-1)
        {
            pickerTime.setCurrentHour(hour);
            pickerTime.setCurrentMinute(minute);
        }
    }

    private void setNotification() {
        if (cbSetting.isChecked()) {
            check = true;
            Calendar calendar = Calendar.getInstance();
            hour = pickerTime.getCurrentHour();
            minute = pickerTime.getCurrentMinute();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            Intent intent1 = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else {
            check =false;
        }
        setValue();
    }
}
