package com.example.phanv.camera.View.AccontView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountInforTask;
import com.example.phanv.camera.Model.AccountModel.AccountInformationInterface;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.Other.HomeActivity;
import com.squareup.picasso.Picasso;

public class ViewAccountActivity extends AppCompatActivity implements View.OnClickListener, AccountInformationInterface {
    private AccountInforTask task;
    private ImageView img;
    private TextView tvLoginName;
    private TextView tvFullName;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvAddres;
    private Button btChangePass;
    private Button btEdit;
    private Button btExit;
    private String linkimg = "";

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
        btExit.setOnClickListener(this);
        task = new AccountInforTask(this, this);
        task.execute();
    }


    @Override
    public void onClick(View view) {
        if (view == btExit) {
            SharedPreferences store = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = store.edit();
            editor.clear().commit();
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
        if (view == btChangePass) {
            Intent intent = new Intent(this, ChangePassActivity.class);
            startActivity(intent);
        }
        if (view == btEdit) {
            Intent intent = new Intent(this, EditAccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("loginName", tvLoginName.getText().toString());
            bundle.putString("fullName", tvFullName.getText().toString());
            bundle.putString("phone", tvPhone.getText().toString());
            bundle.putString("email", tvEmail.getText().toString());
            bundle.putString("address", tvAddres.getText().toString());
            bundle.putString("img", linkimg);
            intent.putExtra("data", bundle);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    public void loadSuccess(Account account) {
        if (account != null) {
            tvAddres.setText(account.getAddress());
            tvEmail.setText(account.getEmail());
            tvPhone.setText(account.getPhone());
            tvFullName.setText(account.getFullName());
            tvLoginName.setText(account.getLoginName());
            try {
                if (account.getImage() != null) {
                    if (account.getImage().length() > 40) {
                        Picasso.with(this).load(account.getImage()).into(img);
                        linkimg = account.getImage();
                    } else {
                        img.setImageResource(R.drawable.img_account);
                    }
                }
            } catch (Exception e) {
                img.setImageResource(R.drawable.img_account);
            }
        }
    }
}
