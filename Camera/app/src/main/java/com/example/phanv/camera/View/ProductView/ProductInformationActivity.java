package com.example.phanv.camera.View.ProductView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.ProductModel.GetProductInforTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductInformationActivity extends FragmentActivity implements View.OnClickListener, EditProductInterface {
    GetProductInforTask task;
    Button btEdit;
    Button btDelete;
    DeleteProductTask taskDelete;
    ImageView img;
    ImageView img1;
    ImageView img2;
    String id;
    ViewPager viewPager;
    public static int number = 0;
    List<String> list = new ArrayList<>();
    PagerAdapter adapter;
    Activity activity;
    int positionImage = 0;
    static Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        taskDelete = new DeleteProductTask(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        activity = this;
        id = bundle.getString("id");
        task = new GetProductInforTask(this, this);
        task.execute(id);
        btEdit = findViewById(R.id.btEditProduct);
        btDelete = findViewById(R.id.btDeleteProduct);
        viewPager = findViewById(R.id.vpDetailImage);
        btDelete.setOnClickListener(this);
        btEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btDelete) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có muốn xóa sản phẩm này?")
                    .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taskDelete.execute(id);
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        }
        if (view == btEdit) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            Intent intent = new Intent(this, EditProductActivity.class);
            intent.putExtra("data", bundle);
            finish();
            startActivity(intent);
        }
        if (view.getId() == R.id.imgAddCamera) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 0;
            status = true;
            setAdapter();
        }
        if (view == img1) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 1;
            status = true;
            setAdapter();
        }
        if (view == img2) {
            viewPager.setVisibility(View.VISIBLE);
            positionImage = 2;
            status = true;
            setAdapter();
        }
    }

    private void setAdapter() {
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionImage);
    }

    @Override
    public void loadSuccess(Product values) {

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
        tvAccessories.setText(values.getAddition());
        tvDescription.setText(values.getDescription());
        tvMega.setText(values.getMega());
        tvName.setText(values.getName());
        tvPrice.setText(values.getPrice());
        tvVideo.setText(values.getNameVideo());
        tvMaker.setText(values.getNameMaker());
        if (values.getStatus().equals("1")) {
            tvStatus.setText("Mới");
        } else {
            tvStatus.setText("Cũ");
        }
        if (values.getImage().length() > 40) {
            Picasso.with(this).load(values.getImage()).into(img);
            number++;
            list.add(values.getImage());
            if (values.getImage1().length() > 40) {
                list.add(values.getImage1());
                Picasso.with(this).load(values.getImage1()).into(img1);
                img1.setVisibility(View.VISIBLE);
                number++;
                if (values.getImage2().length() > 40) {
                    list.add(values.getImage2());
                    Picasso.with(this).load(values.getImage2()).into(img2);
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

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new ViewDetailImageFragment(activity, list, position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getVisibility() == View.VISIBLE) {
            viewPager.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
