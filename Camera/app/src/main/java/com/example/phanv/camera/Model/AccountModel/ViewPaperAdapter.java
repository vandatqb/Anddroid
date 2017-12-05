package com.example.phanv.camera.Model.AccountModel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.phanv.camera.View.AccontView.LoginFragment;
import com.example.phanv.camera.View.AccontView.RegisterFragment;

/**
 * Created by phanv on 27-Nov-17.
 */

public class ViewPaperAdapter extends FragmentStatePagerAdapter {
    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0: {
                frag = new LoginFragment();
                break;
            }
            case 1: {
                frag = new RegisterFragment();
                break;
            }
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Đăng nhập";
                break;
            case 1:
                title = "Đăng kí";
                break;
        }
        return title;
    }
}
