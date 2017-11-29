package com.example.phanv.mydiary.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by phanv on 01-Nov-17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer []mThumbIds;

    public ImageAdapter(Context mContext, Integer[] mThumbIds) {
        this.mContext = mContext;
        this.mThumbIds = mThumbIds;
    }

    public ImageAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if ((view==null)) {
            imageView = new ImageView(mContext);
            //căn hình
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else {
            imageView=(ImageView) view;
        }
        //lấy đúng vị trí hình ảnh được chọn
        //gán lại ImageResource
        imageView.setImageResource(mThumbIds[i]);
        return imageView;
    }
}
