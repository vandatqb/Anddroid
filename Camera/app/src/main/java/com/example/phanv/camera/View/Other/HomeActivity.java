package com.example.phanv.camera.View.Other;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.DataLocalModel.AccountInformation;
import com.example.phanv.camera.Model.DataLocalModel.LocalDataProcess;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.AccontView.AccountActivity;
import com.example.phanv.camera.View.AccontView.LoginActivity;
import com.example.phanv.camera.View.ChatView.ListChatActivity;
import com.example.phanv.camera.View.ProductView.ProductActivity;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LocalDataProcess localDataProcess;
    Boolean loged = false;
    ImageView img;
    TextView tvName;
    AccountInformation accountInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        localDataProcess = new LocalDataProcess(this);
        accountInformation = localDataProcess.read();
        if (!accountInformation.getId().equals("0")) {
            loged = true;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            //img.setImageResource(R.drawable.img_account);
            tvName.setText("Bạn chưa đăng nhập");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
            case R.id.nav_product:{
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
}
