package com.example.phanv.camera.View.ProductView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 30-Nov-17.
 */

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewProductAccount> implements Filterable {
    List<Product> list;
    List<Product> listCopy;
    Activity activity;
    int type;
    int page;

    public ListProductAdapter(List<Product> list, Activity activity, int type, int page) {
        this.list = list;
        this.activity = activity;
        this.listCopy = list;
        this.type = type;
        this.page = page;
    }

    @Override
    public ViewProductAccount onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if (type == 1 || type == 2) {
            v = inflater.inflate(R.layout.product, parent, false);
        } else {
            v = inflater.inflate(R.layout.prodcut_horizontal, parent, false);
        }
        return new ViewProductAccount(v);
    }

    @Override
    public void onBindViewHolder(ViewProductAccount holder, int position) {
        Product pr = list.get(position);
        if (pr.getImage().length() > 40) {
            Picasso.with(activity).load(pr.getImage()).into(holder.img);
        }
        if (type == 0) {
            holder.tvPrice.setText(pr.getPrice());
            holder.tvName.setText(pr.getName());
            holder.tvId.setText(pr.getId());
            holder.tvIdAccount.setText(pr.getIdAccount());
        } else {
            if (type == 2) {
                holder.tvName.setText(pr.getName());
                holder.tvPrice.setText("Giá : " + pr.getPrice());
                holder.tvId.setText(pr.getId());
                holder.tvIdAccount.setText(pr.getIdAccount());
                holder.tvTime.setText("Đăng bán lúc : " + pr.getTime());
            } else {
                holder.tvTime.setText("Đăng lúc : " + pr.getTime());
                holder.tvPrice.setText("Giá : " + pr.getPrice());
                holder.tvName.setText(pr.getName());
                holder.tvId.setText(pr.getId());
                holder.tvIdAccount.setText(pr.getIdAccount());
                if (pr.getStatus().equals("1")) {
                    holder.tvStatus.setText(" - Đã duyệt");
                } else {
                    holder.tvStatus.setText(" - Chưa duyệt");
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = listCopy;
                } else {
                    ArrayList<Product> listResult = new ArrayList<>();
                    for (Product product : listCopy) {
                        if (product.getName().toLowerCase().contains(search.toLowerCase())) {
                            listResult.add(product);
                        }
                    }
                    list = listResult;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewProductAccount extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvStatus;
        TextView tvTime;
        TextView tvId;
        TextView tvIdAccount;

        public ViewProductAccount(View itemView) {
            super(itemView);
            if (type == 0) {
                img = itemView.findViewById(R.id.imgProductHorizontal);
                tvName = itemView.findViewById(R.id.tvNameProductHorizontal);
                tvPrice = itemView.findViewById(R.id.tvPriceProductHorizontal);
                tvId = itemView.findViewById(R.id.tvIdProductHorizontal);
                tvIdAccount = itemView.findViewById(R.id.tvIdAccountHorizontal);

            } else {
                img = itemView.findViewById(R.id.imgListProductId);
                tvName = itemView.findViewById(R.id.tvListProductName);
                tvPrice = itemView.findViewById(R.id.tvListProductPrice);
                tvStatus = itemView.findViewById(R.id.tvListproductStatus);
                tvTime = itemView.findViewById(R.id.tvListProductTime);
                tvId = itemView.findViewById(R.id.tvListProductId);
                tvIdAccount = itemView.findViewById(R.id.tvListProductIdAccount);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", tvId.getText().toString());

                    Intent intent;
                    if (page == 0) {
                        intent = new Intent(activity, ViewProductActivity.class);
                        bundle.putString("idAccount", tvIdAccount.getText().toString());
                    } else {
                        intent = new Intent(activity, ProductInformationActivity.class);
                        activity.finish();
                    }
                    intent.putExtra("data", bundle);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
