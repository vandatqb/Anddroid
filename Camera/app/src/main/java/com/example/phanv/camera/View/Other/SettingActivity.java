package com.example.phanv.camera.View.Other;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.phanv.camera.Model.AccountModel.Setting;
import com.example.phanv.camera.Model.ChatModel.ChatService;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Model.ProductModel.ProductService;
import com.example.phanv.camera.R;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Switch swMessage;
    private Switch swNewProduct;
    private Button btApply;
    private TextView tvprice;
    private Number min = 0;
    private Number max = 150;
    private Intent service;
    private Intent serviceMessage;
    private DataLocalProcess process;
    private Boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        layout = findViewById(R.id.layoutNotifi);
        swMessage = findViewById(R.id.swMessage);
        swNewProduct = findViewById(R.id.swProdduct);
        tvprice = findViewById(R.id.tvPrice);
        btApply = findViewById(R.id.btApply);
        process = new DataLocalProcess(this);
        loadSetting();
        setEvent();
        service = new Intent(SettingActivity.this, ProductService.class);
        serviceMessage = new Intent(SettingActivity.this, ChatService.class);
    }

    private void loadSetting() {
        Setting setting = process.readSetting();
        if (setting.isSetting()) {
            swNewProduct.setChecked(true);
            status = true;
            layout.setVisibility(View.VISIBLE);
            tvprice.setText(setting.getMin() + " - " + setting.getMax() + " triệu");
            min = setting.getMin();
            max = setting.getMax();
        }
    }

    private void setEvent() {
        swMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
//                    serviceMessage.putExtra("idAccount", MainActivity.idAccount);
//                    startService(serviceMessage);
                }
                else {
                    //stopService(serviceMessage);
                }
            }
        });
        swNewProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    layout.setVisibility(View.VISIBLE);
                    loadSetting();
                } else {
                    stopService(service);
                    process.writeSetting(false, Integer.parseInt(min.toString()), Integer.parseInt(max.toString()));
                    layout.setVisibility(View.INVISIBLE);
                }
            }
        });
        final CrystalRangeSeekbar rangeSeekbar = findViewById(R.id.rangeSeekBar);
        if (status) {
            rangeSeekbar.setMinStartValue(Float.parseFloat(min.toString())).setMaxStartValue(Float.parseFloat(max.toString())).apply();
        }
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvprice.setText(minValue + " - " + maxValue + " triệu");
                min = minValue;
                max = maxValue;

            }
        });
        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minInt = Integer.parseInt(min.toString());
                int maxInt = Integer.parseInt(max.toString());
                process.writeSetting(true, minInt, maxInt);
                service.putExtra("min", minInt);
                service.putExtra("max", maxInt);
                service.putExtra("idAccount", MainActivity.idAccount);
                startService(service);
            }
        });
    }

    @Override
    public Resources getResources() {

        return super.getResources();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
