package com.example.phanv.camera.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phanv.camera.Model.ListProductAccountAdapter;
import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Model.Product;
import com.example.phanv.camera.Process.LocalData;
import com.example.phanv.camera.Process.ProductProcess;
import com.example.phanv.camera.R;

import java.util.List;

public class ProductFragment extends Fragment {
    ListProductAccountAdapter adapter;
    List<Product> list;
    RecyclerView rcvListProduct;
    ProductProcess process= new ProductProcess();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        rcvListProduct=view.findViewById(R.id.rcvProduct);
        LocalData data = new LocalData(getActivity());
        Local local=data.read();
        list=process.getAllProductWithId(local.getId());
        adapter=new ListProductAccountAdapter(list,getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListProduct.setLayoutManager(layoutManager);
        rcvListProduct.setAdapter(adapter);
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
}
