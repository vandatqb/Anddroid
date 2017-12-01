package com.example.phanv.camera.Model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phanv on 30-Nov-17.
 */

public class ListProductAccountAdapter extends RecyclerView.Adapter<ListProductAccountAdapter.ViewProductAccount> implements Filterable {
    List<Product> list;
    List<Product> listCopy;
    Activity activity;

    public ListProductAccountAdapter(List<Product> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        this.listCopy = list;
    }

    @Override
    public ViewProductAccount onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product, parent, false);
        return new ViewProductAccount(view);
    }

    @Override
    public void onBindViewHolder(ViewProductAccount holder, int position) {
        Product pr = list.get(position);
        if (pr.getImage().length() > 40) {
            Picasso.with(activity).load(pr.getImage()).into(holder.img);
        }
        holder.tvTime.setText("Đăng lúc : "+pr.getTime());
        holder.tvPrice.setText("Giá : "+pr.getPrice());
        holder.tvName.setText(pr.getName());
        holder.tvId.setText(pr.getId());
        if (pr.getStatus().equals("1")) {
            holder.tvStatus.setText(" - Đã duyệt");
        } else {
            holder.tvStatus.setText(" - Chưa duyệt");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewProductAccount extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvStatus;
        TextView tvTime;
        TextView tvId;

        public ViewProductAccount(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgListProductId);
            tvName = itemView.findViewById(R.id.tvListProductName);
            tvPrice = itemView.findViewById(R.id.tvListProductPrice);
            tvStatus = itemView.findViewById(R.id.tvListproductStatus);
            tvTime = itemView.findViewById(R.id.tvListProductTime);
            tvId = itemView.findViewById(R.id.tvListProductId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent =new Intent(activity,)
                }
            });
        }
    }
}
