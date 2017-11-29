package com.example.phanv.camera.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.Account;
import com.example.phanv.camera.Model.Property;
import com.example.phanv.camera.R;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    TextView tvLoginName;
    TextView tvFullName;
    TextView tvPhone;
    TextView tvEmail;
    TextView tvAddres;
    Button btChangePass;
    Button btEdit;
    Button btExit;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        img = findViewById(R.id.imgViewAccount);
        tvLoginName = findViewById(R.id.tvLGName);
        tvFullName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddres = findViewById(R.id.tvAddress);
        btChangePass = findViewById(R.id.btEditPass);
        btEdit = findViewById(R.id.btEditAccount);
        btExit = findViewById(R.id.btExit);
        btEdit.setOnClickListener(this);
        btChangePass.setOnClickListener(this);
        btEdit.setOnClickListener(this);
        getInformation();
        viewInformation();
    }

    private void getInformation() {
        List<Property> list= new ArrayList<>();
        Property id = new Property()
    }
    private void viewInformation(){

    }

    @Override
    public void onClick(View view) {

    }
}
