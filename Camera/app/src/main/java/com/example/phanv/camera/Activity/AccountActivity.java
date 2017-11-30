package com.example.phanv.camera.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.Account;
import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Model.Property;
import com.example.phanv.camera.Process.AccountProcess;
import com.example.phanv.camera.Process.LocalData;
import com.example.phanv.camera.R;
import com.squareup.picasso.Picasso;

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
    AccountProcess process = new AccountProcess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        btExit.setOnClickListener(this);
        getInformation();
        viewInformation();
    }

    private void getInformation() {
        LocalData data = new LocalData(this);
        Local local = data.read();
        account = process.getAccountInformation(local.getId());
    }

    private void viewInformation() {
        if (account != null) {
            tvAddres.setText(account.getAddress());
            tvEmail.setText(account.getEmail());
            tvPhone.setText(account.getPhone());
            tvFullName.setText(account.getFullName());
            tvLoginName.setText(account.getLoginName());
            if (account.getImage().length() > 40) {
                Picasso.with(this).load(account.getImage()).into(img);
            } else {
                img.setImageResource(R.drawable.img_account);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btExit) {
            SharedPreferences store = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = store.edit();
            editor.clear().commit();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        if(view==btChangePass)
        {
            Intent intent = new Intent(this, ChangePassActivity.class);
            startActivity(intent);
        }
        if(view==btEdit)
        {

            Intent intent = new Intent(this, EditAccountActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
