package com.example.phanv.mydiary.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.phanv.mydiary.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectDateActivity extends AppCompatActivity {
    CalendarView cldView;
    Button btWrite;
    String date = "";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        cldView = (CalendarView) findViewById(R.id.cldView);
        btWrite = (Button) findViewById(R.id.btWrite);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date = sdf.format(new Date());
        cldView.setMaxDate(Long.valueOf(new Date().getTime()));
        cldView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String ngay = i2<10?"0"+i2:i2+"";
                String thang = i1<10?"0"+i1+1:i1+1+"";
                date = ngay+"/"+thang+"/"+i;
            }
        });

        btWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle =new Bundle();
                bundle.putString("date",date);
                Intent intent =new Intent(SelectDateActivity.this,WriteDiaryActivity.class);
                intent.putExtra("date",bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
