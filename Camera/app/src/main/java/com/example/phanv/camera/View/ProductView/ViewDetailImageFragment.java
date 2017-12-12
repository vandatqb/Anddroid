package com.example.phanv.camera.View.ProductView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.phanv.camera.R;
import com.squareup.picasso.Picasso;

import java.util.List;


@SuppressLint("ValidFragment")
public class ViewDetailImageFragment extends Fragment {
    int position;
    Activity activity;
    List<String> list;

    @SuppressLint("ValidFragment")
    public ViewDetailImageFragment(Activity activity, List<String> list, int position) {
        this.position = position;
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.view_image_slide, container, false);
        ImageView imageView = rootView.findViewById(R.id.imgForViewPaper);
        Picasso.with(activity).load(list.get(position)).into(imageView);
        return rootView;
    }
}
