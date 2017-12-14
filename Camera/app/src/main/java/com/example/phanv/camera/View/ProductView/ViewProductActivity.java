package com.example.phanv.camera.View.ProductView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountInforTask;
import com.example.phanv.camera.Model.AccountModel.AccountInformationInterface;
import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Model.ProductModel.FavouriteTask;
import com.example.phanv.camera.Model.ProductModel.GetProductInforTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ChatView.ChatActivity;
import com.example.phanv.camera.View.Other.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity implements EditProductInterface, AccountInformationInterface, View.OnClickListener {
    GetProductInforTask taskGetInformation;
    AccountInforTask taskAccount;
    String idProduct;
    String idAccount;
    int number = 0;
    Activity activity;
    List<String> list = new ArrayList<>();
    ViewPager viewPager;
    PagerAdapter adapter;
    int positionImage = 0;
    ImageView img1;
    ImageView img2;
    ImageView img;
    TextView tvName;
    ImageView imgAccount;
    ImageView imgCall;
    ImageView imgMessage;
    ImageView imgInformation;
    ImageView imgFavorite;
    String phone = "";
    FavouriteTask taskFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        getIntentData();
        taskGetInformation = new GetProductInforTask(this, this);
        taskGetInformation.execute(idProduct);
        taskAccount = new AccountInforTask(this, this);
        taskAccount.execute(idAccount);
        activity = this;
        viewPager = findViewById(R.id.vpDetailImage);
        tvName = findViewById(R.id.tvNameAccount);
        imgCall = findViewById(R.id.actionCall);
        imgMessage = findViewById(R.id.actionChat);
        imgAccount = findViewById(R.id.imgAccountInfor);
        imgFavorite = findViewById(R.id.actionFavourite);
        imgInformation = findViewById(R.id.actionView);
        imgMessage.setOnClickListener(this);
        imgCall.setOnClickListener(this);
        imgInformation.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        idProduct = bundle.getString("id");
        idAccount = bundle.getString("idAccount");
    }

    private void setAdapter() {
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionImage);
    }

    @Override
    public void loadSuccess(Account account) {
        tvName.setText(account.getFullName());
        phone = account.getPhone();
        if (account.getImage().length() > 40) {
            Picasso.with(this).load(account.getImage()).into(imgAccount);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ViewDetailImageFragment(activity, list, position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void loadSuccess(Product product) {
        TextView tvName = findViewById(R.id.tvCameraName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvVideo = findViewById(R.id.tvVideo);
        TextView tvStatus = findViewById(R.id.tvStatus);
        TextView tvMega = findViewById(R.id.tvMega);
        TextView tvAccessories = findViewById(R.id.tvAccessories);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvMaker = findViewById(R.id.tvNameMaker);
        img = findViewById(R.id.imgAddCamera);
        img1 = findViewById(R.id.imgAddCamera1);
        img2 = findViewById(R.id.imgAddCamera2);
        img.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        tvAccessories.setText(product.getAddition());
        tvDescription.setText(product.getDescription());
        tvMega.setText(product.getMega());
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice());
        tvVideo.setText(product.getNameVideo());
        tvMaker.setText(product.getNameMaker());
        if (product.getStatus().equals("1")) {
            tvStatus.setText("Mới");
        } else {
            tvStatus.setText("Cũ");
        }
        if (product.getImage().length() > 40) {
            Picasso.with(this).load(product.getImage()).into(img);
            number++;
            list.add(product.getImage());
            if (product.getImage1().length() > 40) {
                list.add(product.getImage1());
                Picasso.with(this).load(product.getImage1()).into(img1);
                img1.setVisibility(View.VISIBLE);
                number++;
                if (product.getImage2().length() > 40) {
                    list.add(product.getImage2());
                    Picasso.with(this).load(product.getImage2()).into(img2);
                    img2.setVisibility(View.VISIBLE);
                    number++;
                }
            }
        } else {
            img.setImageResource(R.drawable.img_account);
        }
    }

    @Override
    public void EditSuccess() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgAddCamera) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 0;
            setAdapter();
        }
        if (view == img1) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 1;
            setAdapter();
        }
        if (view == img2) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 2;
            setAdapter();
        }
        if (view == imgCall) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);
        }
        if (view == imgMessage) {
            if(MainActivity.loged){
                if (!idAccount.equals(MainActivity.idAccount)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idAccount);
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("id", bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Bạn không thể nhắn tin cho chính bạn", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(activity, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }

        }
        if (view == imgInformation) {
            Intent intent = new Intent(this, ViewDetailAccountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", idAccount);
            intent.putExtra("id", bundle);
            startActivity(intent);
        }
        if (view == imgFavorite) {
            taskFavourite = new FavouriteTask(this);
            taskFavourite.execute(idProduct);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getVisibility() == View.VISIBLE) {
            viewPager.setVisibility(View.INVISIBLE);
        } else {
            finish();
            super.onBackPressed();
        }
    }
}
