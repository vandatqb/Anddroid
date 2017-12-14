package com.example.phanv.camera.View.ProductView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phanv.camera.Model.ProductModel.Maker;

import java.util.List;

/**
 * Created by phanv on 04-Dec-17.
 */

public class MakerSpinerAdapter extends ArrayAdapter<Maker> {
    private Context context;
    private List<Maker> makers;

    public MakerSpinerAdapter(Context context, int resource, List<Maker> makers) {
        super(context, resource, makers);
        this.context = context;
        this.makers = makers;
    }

    @Override
    public int getCount() {
        return makers.size();
    }

    @Nullable
    @Override
    public Maker getItem(int position) {
        return makers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvMaker = new TextView(context);
        tvMaker.setText(makers.get(position).getNameMaker());
        return tvMaker;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvSelected = new TextView(context);
        tvSelected.setText(makers.get(position).getNameMaker());
        return tvSelected;
    }
}
