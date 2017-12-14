package com.example.phanv.camera.View.ProductView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phanv.camera.Model.ProductModel.ListProductTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;

import java.util.List;

public class FavoriteProductFragment extends Fragment implements GetListProductInterface {
    ListProductAdapter adapter;
    RecyclerView rcvListFavoriteProduct;
    ListProductTask task;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_product, container, false);
        rcvListFavoriteProduct =view.findViewById(R.id.rcvFavoriteProduct);
        task = new ListProductTask(this,getActivity());
        task.execute("favorite");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void loadSuccess(List<Product> list) {
        adapter = new ListProductAdapter(list, this.getActivity(), 1, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListFavoriteProduct.setLayoutManager(layoutManager);
        rcvListFavoriteProduct.setAdapter(adapter);
    }
}