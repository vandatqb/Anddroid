package com.example.phanv.camera.View.ProductView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by phanv on 30-Nov-17.
 */

public class ProducAdapter extends FragmentStatePagerAdapter {
    public ProducAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        if (position == 0) {
            frag = new ProductFragment();
        } else {
            if (position == 1) {
                frag = new FavoriteProductFragment();
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
        if (position == 0) {
            title = "Máy ảnh của bạn";
        } else {
            if (position == 1) {
                title = "Máy ảnh yêu thích";
            }
        }
        return title;
    }
}
