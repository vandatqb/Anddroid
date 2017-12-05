package com.example.phanv.camera.View.Other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.phanv.camera.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btHome = findViewById(R.id.btHome);
        btHome.setOnClickListener(this);
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == btHome) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
