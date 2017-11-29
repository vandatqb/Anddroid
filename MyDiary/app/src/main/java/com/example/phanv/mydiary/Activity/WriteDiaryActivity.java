package com.example.phanv.mydiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.mydiary.Model.Diary;
import com.example.phanv.mydiary.R;

public class WriteDiaryActivity extends AppCompatActivity {
    TextView tvDate;
    EditText edTitle;
    EditText edDetail;
    String date;
    Button btSave;
    DatabaseCreate db;
    Context context;
    Boolean status =false;
    Diary diary;
    String errMessage;
    int  id;
    ImageView imgView;
    String url="";
    RatingBar rtFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        getDate();
        setID();
        loadDiary();
    }

    private void loadDiary() {
        context=this;
        db = new DatabaseCreate(context);
        String sq = "Select * from Diary where date='"+date+"'";
        diary = db.getDiary(sq);
        if (diary!=null) {
            status=true;
            edTitle.setText(diary.getTitle());
            edDetail.setText(diary.getDetail());
            id =diary.getId();
            String filePath = diary.getUrl();
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
            imgView.setImageBitmap(yourSelectedImage);
            rtFavorite.setRating(diary.getLike());
            url =diary.getUrl();
        }
    }

    private void getDate() {
        //lấy intent gọi Activity này
        Intent callerIntent=getIntent();
        //có intent rồi thì lấy Bundle dựa vào MyPackage
        Bundle packageFromCaller= callerIntent.getBundleExtra("date");
        //Có Bundle rồi thì lấy các thông số dựa vào soa, sob
        date =packageFromCaller.getString("date");
    }

    private void setID() {
        tvDate =(TextView) findViewById(R.id.tvDate);
        edTitle = (EditText) findViewById(R.id.edTitle);
        edDetail = (EditText) findViewById(R.id.edDetail);
        btSave = (Button) findViewById(R.id.btSave);
        imgView = (ImageView) findViewById(R.id.imgDiary);
        rtFavorite = (RatingBar) findViewById(R.id.rtFavorite);
        String diary = getResources().getString(R.string.diary);
        tvDate.setText(diary+" : "+date);
        context=this;
        db=new DatabaseCreate(context);
        processData();
    }

    private void processData() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTitle.getText().toString().equals("") || edDetail.getText().equals(""))
                {
                    String err =getResources().getString(R.string.error_message);
                    Toast.makeText(WriteDiaryActivity.this,err,Toast.LENGTH_SHORT).show();
                }
                else {
                    float favorite =rtFavorite.getRating();
                    Diary diary =new Diary(id,date,edTitle.getText().toString(),edDetail.getText().toString(),favorite,url);
                    if (status==false)
                    {
                        if (db.insertDiary(diary)>0)
                        {

                            Bundle bundle =new Bundle();
                            bundle.putString("date",date);
                            Intent intent =new Intent(WriteDiaryActivity.this,ViewDiary.class);
                            intent.putExtra("date",bundle);
                            finish();
                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(WriteDiaryActivity.this,errMessage,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if (db.updateDiary(diary)==true)
                        {
                            Bundle bundle =new Bundle();
                            bundle.putString("date",date);
                            Intent intent =new Intent(WriteDiaryActivity.this,ViewDiary.class);
                            intent.putExtra("date",bundle);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(WriteDiaryActivity.this,"update "+errMessage,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    url =filePath;
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    imgView.setImageBitmap(yourSelectedImage);
                }
        }
    };
}
