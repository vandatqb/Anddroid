package com.example.phanv.mydiary.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.mydiary.Model.Diary;
import com.example.phanv.mydiary.R;

public class ViewDiary extends AppCompatActivity implements View.OnClickListener {
    String date="";
    TextView tvTitle;
    TextView tvDetail;
    Button btHome;
    Button btEdit;
    DatabaseCreate db;
    Diary diary;
    Context context;
    Button btDel;
    ImageView imgView;
    RatingBar rtFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);
        setUp();
        loadDiary();
    }

    private void loadDiary() {
        String sq = "Select * from Diary where date='"+date+"'";
        diary = db.getDiary(sq);
        tvTitle.setText(diary.getDate()+"\n("+diary.getTitle()+")");
        tvDetail.setText(diary.getDetail());
        String filePath = diary.getUrl();
        Bitmap yourSelectedImage;
        if(filePath.length()>5) {
            yourSelectedImage = BitmapFactory.decodeFile(filePath);
            imgView.setImageBitmap(yourSelectedImage);
        }
        else
        {
            imgView.setImageResource(R.drawable.diary_icon_final);
        }

        rtFavorite.setRating(diary.getLike());
    }
    private void setUp() {
        context=this;
        db=new DatabaseCreate(context);
        getDate();
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btHome = (Button) findViewById(R.id.btHome);
        btEdit = (Button) findViewById(R.id.btEdit);
        btDel= (Button) findViewById(R.id.btDel);
        btEdit.setOnClickListener(this);
        btHome.setOnClickListener(this);
        btDel.setOnClickListener(this);
        rtFavorite = (RatingBar) findViewById(R.id.rtFavoriteView);
        imgView = (ImageView) findViewById(R.id.imgView);
    }

    private void getDate() {
        //lấy intent gọi Activity này
        Intent callerIntent=getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller= callerIntent.getBundleExtra("date");
        //Có Bundle rồi thì lấy các thông số dựa vào soa, sob
        date =packageFromCaller.getString("date");
    }

    @Override
    public void onClick(View view) {
        if (view==btHome)
        {
            finish();
            Intent intent = new Intent(ViewDiary.this,MainActivity.class);
            startActivity(intent);
        }
        else
        {
            if ((view==btEdit)) {
                finish();
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                Intent intent = new Intent(ViewDiary.this, WriteDiaryActivity.class);
                intent.putExtra("date", bundle);
                startActivity(intent);
            }
            else
            {
                if(view==btDel)
                {
                    showAlertDialog();
                }
            }
        }
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.titleDealog));
        builder.setMessage(getResources().getString(R.string.messageDialog));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String where = "date = '"+date+"'";
                if (db.deleteDiary(where)==true)
                {
                    dialogInterface.dismiss();
                    Toast.makeText(ViewDiary.this,getResources().getString(R.string.deleteSuccess),Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(ViewDiary.this,getResources().getString(R.string.error_message),Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
