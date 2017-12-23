package com.example.phanv.camera.View.Other;

import android.app.Activity;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Model.ProductModel.GetListMakerTask;
import com.example.phanv.camera.Model.ProductModel.GetListMenuProductTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.AccontView.LoginActivity;
import com.example.phanv.camera.View.AccontView.ViewAccountActivity;
import com.example.phanv.camera.View.ChatView.ListChatActivity;
import com.example.phanv.camera.View.ProductView.ListProductAdapter;
import com.example.phanv.camera.View.ProductView.ProductActivity;
import com.example.phanv.camera.View.SearchView.SearchProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {
    public static String idAccount;
    public static String loginName;
    //private DataLocalPresenter presenter;
    private DataLocalProcess process;
    public static Boolean loged = false;
    private ImageView img;
    private TextView tvName;
    private AccountInformation accountInformation;
    private GetListMakerTask task;
    private RecyclerView rcvNew;
    private RecyclerView rcvNikon;
    private RecyclerView rcvCanon;
    private RecyclerView rcvUderTenMillion;
    private ListProductAdapter adapter;
    private GetListMenuProductTask taskListNew;
    private GetListMenuProductTask taskListNikon;
    private GetListMenuProductTask taskListCanon;
    private GetListMenuProductTask taskUnderTenMillion;
    private Activity activity;
    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        rcvCanon = findViewById(R.id.rcvCanonProduct);
        rcvNew = findViewById(R.id.rcvNewProduct);
        rcvNikon = findViewById(R.id.rcvNikonProduct);
        rcvUderTenMillion = findViewById(R.id.rcvUnderTenMillion);
        activity = this;
        searchView = findViewById(R.id.svHome);
        searchView.setOnQueryTextListener(this);
        //presenter = new DataLocalPresenter(activity);
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

    public void loadListUnderTenMillion(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 0, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvUderTenMillion.setLayoutManager(layoutManager);
        rcvUderTenMillion.setAdapter(adapter);
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
        process = new DataLocalProcess(this);
        accountInformation = process.read();
        if (!accountInformation.getId().equals("0")) {
            idAccount = accountInformation.getId();
            loginName = accountInformation.getLoginName();
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
                    Intent intent = new Intent(this, ViewAccountActivity.class);
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
            case R.id.nav_setting: {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
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
            Toast.makeText(this, "Đang làm mới", Toast.LENGTH_SHORT).show();
            load();

        }
        return super.onOptionsItemSelected(item);
    }

    private void load() {
        taskListNew = new GetListMenuProductTask(this, 0);
        taskListNikon = new GetListMenuProductTask(this, 1);
        taskListCanon = new GetListMenuProductTask(this, 2);
        taskUnderTenMillion = new GetListMenuProductTask(this, 3);
        taskListNew.execute(0);
        taskListNikon.execute(1);
        taskListCanon.execute(2);
        taskUnderTenMillion.execute(3);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.length() > 1) {
            Intent intent = new Intent(this, SearchProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("value", s);
            intent.putExtra("data", bundle);
            startActivity(intent);
        } else {
            Toast.makeText(activity, "Nội dung tìm kiếm quá ngắn", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
