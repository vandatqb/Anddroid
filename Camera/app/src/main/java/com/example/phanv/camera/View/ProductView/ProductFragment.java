package com.example.phanv.camera.View.ProductView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phanv.camera.Model.ProductModel.ListProductTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;

import java.util.List;

public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener,GetListProductInterface {
    ListProductAdapter adapter;
    RecyclerView rcvListProduct;
    SearchView searchView;
    ListProductTask task;
    FloatingActionButton fabAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        rcvListProduct = view.findViewById(R.id.rcvProduct);
        searchView = view.findViewById(R.id.svProduct);
        fabAdd=view.findViewById(R.id.fab);
        searchView.setOnQueryTextListener(this);
        task = new ListProductTask(this,getActivity());
        task.execute("1");
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddProductActivity.class);
                startActivity(intent);
            }
        });
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);
        Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void loadSuccess(List<Product> list) {
        adapter = new ListProductAdapter(list, this.getActivity(), 1, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListProduct.setLayoutManager(layoutManager);
        rcvListProduct.setAdapter(adapter);
    }
}
