package com.example.phanv.camera.View.Other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.Model.ProductModel.GetListMakerTask;
import com.example.phanv.camera.Model.ProductModel.GetListMenuProduct;
import com.example.phanv.camera.Model.ProductModel.ListProductAdapter;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.AccontView.AccountActivity;
import com.example.phanv.camera.View.AccontView.LoginActivity;
import com.example.phanv.camera.View.ChatView.ListChatActivity;
import com.example.phanv.camera.View.ProductView.ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LocalDataProcess localDataProcess;
    public static Boolean loged = false;
    ImageView img;
    TextView tvName;
    AccountInformation accountInformation;
    GetListMakerTask task;
    RecyclerView rcvNew;
    RecyclerView rcvNikon;
    RecyclerView rcvCanon;
    ListProductAdapter adapter;
    GetListMenuProduct taskListNew;
    GetListMenuProduct taskListNikon;
    GetListMenuProduct taskListCanon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        rcvCanon = findViewById(R.id.rcvCanonProduct);
        rcvNew = findViewById(R.id.rcvNewProduct);
        rcvNikon = findViewById(R.id.rcvNikonProduct);
        setSupportActionBar(toolbar);
        getListMakerAndVideo();
        getDataLocal();
        drawMenu(toolbar);
        load();
    }

    public void loadNewProduct(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 0, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvNew.setLayoutManager(layoutManager);
        rcvNew.setAdapter(adapter);
    }

    public void loadNikonProduct(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 0, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvNikon.setLayoutManager(layoutManager);
        rcvNikon.setAdapter(adapter);
    }

    public void loadCanonProduct(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 0, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvCanon.setLayoutManager(layoutManager);
        rcvCanon.setAdapter(adapter);
    }

    private void drawMenu(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getDataLocal() {
        localDataProcess = new LocalDataProcess(this);
        accountInformation = localDataProcess.read();
        if (!accountInformation.getId().equals("0")) {
            loged = true;
        }
    }

    private void getListMakerAndVideo() {
        task = new GetListMakerTask(this);
        task.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_message: {
                if (loged) {
                    Intent intent = new Intent(this, ListChatActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.nav_account: {
                if (loged) {
                    Intent intent = new Intent(this, AccountActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.nav_product: {
                if (loged) {
                    Intent intent = new Intent(this, ProductActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        img = findViewById(R.id.imgMenu);
        tvName = findViewById(R.id.tvNameMenu);

        if (loged) {

            if (accountInformation.getImgae().length() > 40) {
                Picasso.with(getBaseContext()).load(accountInformation.getImgae()).into(img);
            } else {
                img.setImageResource(R.drawable.img_account);
            }
            tvName.setText(accountInformation.getFullName() + "(" + accountInformation.getLoginName() + ")");
        } else {
            img.setImageResource(R.drawable.img_account);
            tvName.setText("Bạn chưa đăng nhập");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Refeshsing", Toast.LENGTH_SHORT).show();
            load();

        }
        return super.onOptionsItemSelected(item);
    }

    private void load() {
        taskListNew = new GetListMenuProduct(this, 0);
        taskListNikon = new GetListMenuProduct(this, 1);
        taskListCanon = new GetListMenuProduct(this, 2);
        taskListNew.execute(0);
        taskListNikon.execute(1);
        taskListCanon.execute(2);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}
