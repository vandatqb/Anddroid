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

import com.example.phanv.camera.Model.ProductModel.ListProductAdapter;
import com.example.phanv.camera.Model.ProductModel.ListProductTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;

import java.util.List;

public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener {
    ListProductAdapter adapter;
    RecyclerView rcvListProduct;
    SearchView searchView;
    FloatingActionButton fabAdd;
    ListProductTask task;

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
        searchView.setOnQueryTextListener(this);
        fabAdd = view.findViewById(R.id.fab);
        task = new ListProductTask(this);
        task.execute();
        rcvListProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAdd.getVisibility() == View.VISIBLE) {
                    fabAdd.hide();
                } else if (dy < 0 && fabAdd.getVisibility() != View.VISIBLE) {
                    fabAdd.show();
                }
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        return view;
    }

    public void loadData(List<Product> list) {

        adapter = new ListProductAdapter(list, this.getActivity(), 1, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListProduct.setLayoutManager(layoutManager);
        rcvListProduct.setAdapter(adapter);
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
}
