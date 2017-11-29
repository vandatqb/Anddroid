package com.example.phanv.mydiary.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.phanv.mydiary.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgFace;
    ImageView imgMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imgFace = (ImageView) findViewById(R.id.imgFace);
        imgMail = (ImageView) findViewById(R.id.imgMail);
        imgMail.setOnClickListener(this);
        imgFace.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == imgFace)
        {
            Intent intentFb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fb.me/vandatqb"));
            startActivity(intentFb);
        }
        if(view == imgMail)
        {
            String subject = getResources().getString(R.string.feedbackDiary);
            String body = getResources().getString(R.string.bodyMail);
            String btSend = getResources().getString(R.string.sendMail);
            Intent intentMail = new Intent(Intent.ACTION_SEND);
            intentMail.setType("message/rfc822");
            intentMail.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vandat03@gmail.com"});
            intentMail.putExtra(Intent.EXTRA_SUBJECT, subject);
            intentMail.putExtra(Intent.EXTRA_TEXT   , body);
            try {
                startActivity(Intent.createChooser(intentMail, btSend));
            } catch (android.content.ActivityNotFoundException ex) {
                String err = getResources().getString(R.string.error_message);
                Toast.makeText(AboutActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
