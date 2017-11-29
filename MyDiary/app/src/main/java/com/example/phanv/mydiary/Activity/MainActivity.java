package com.example.phanv.mydiary.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.phanv.mydiary.Model.ImageAdapter;
import com.example.phanv.mydiary.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    GridView gridView;
    //mảng lưu danh sách các id hình ảnh có sẵn
    Integer[]mThumbIds;
    //Adapter cho GridView
    ImageAdapter adapter=null;
    //Lưu Bundle backup cho MainActivity
    Bundle myBackupBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Lưu savedInstanceState trước vào myBackupBundle
        myBackupBundle=savedInstanceState;
        setContentView(R.layout.activity_main);
        //gán mảng các Id hình ảnh cho mThumbIds
        mThumbIds=new Integer[]{R.drawable.add_blue,R.drawable.diary_icon_final,
                R.drawable.tip,
                R.drawable.set,R.drawable.about,};
        gridView=(GridView) findViewById(R.id.grView);
        //thiết lập Datasource cho Adapter
        adapter=new ImageAdapter(this, mThumbIds);
        //gán Adapter vào Gridview
        gridView.setAdapter(adapter);
        //thiết lập sự kiện để mở từng hình ảnh chitiết
        gridView.setOnItemClickListener(this);
    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //gọi hàm xem hình ảnh chi tiết tại ví trí thứ arg2
        if(arg2==0)
        {
            Intent intent =new Intent(MainActivity.this,SelectDateActivity.class);
            startActivity(intent);
        }
        if(arg2==1)
        {
            Intent intent =new Intent(MainActivity.this,ListDiaryActivity.class);
            startActivity(intent);
        }

        if(arg2==3)
        {
            Intent intent =new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        if(arg2==4)
        {
            Intent intent =new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.exitDiary));
        builder.setMessage(getResources().getString(R.string.exitConfirm));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
