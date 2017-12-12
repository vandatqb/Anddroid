package com.example.phanv.camera.Model.ChatModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ChatView.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanv on 27-Nov-17.
 */

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.View> implements Filterable {
    List<ListCameraChat> data;
    List<ListCameraChat> dataCopy;
    Activity activity;

    public ListChatAdapter(List<ListCameraChat> list, Activity activity) {
        this.data = list;
        this.dataCopy = list;
        this.activity = activity;
    }

    @Override
    public ListChatAdapter.View onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        android.view.View view = inflater.inflate(R.layout.list_chat, parent, false);
        return new View(view);
    }

    @Override
    public void onBindViewHolder(ListChatAdapter.View holder, int position) {
        ListCameraChat chat = data.get(position);
        if (chat.getImg().length() < 10) {
            holder.img.setImageResource(R.drawable.img_account);
        } else {
            Picasso.with(activity).load(chat.getImg()).into(holder.img);
        }
        holder.tvName.setText(chat.getName());
        holder.tvId.setText(chat.getId());
        holder.tvTime.setText(chat.getTime());
        holder.tvContent.setText(chat.getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();

                if (search.isEmpty()) {
                    data = dataCopy;
                } else {
                    ArrayList<ListCameraChat> listResult = new ArrayList<>();
                    for (ListCameraChat chat : dataCopy) {
                        if (chat.getName().toLowerCase().contains(search.toLowerCase())) {
                            listResult.add(chat);
                        }
                    }
                    data = listResult;
                }
                FilterResults results = new FilterResults();
                results.values = data;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<ListCameraChat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class View extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvId;
        TextView tvContent;
        TextView tvTime;

        @SuppressLint("WrongViewCast")
        public View(android.view.View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgListChat);
            tvName = itemView.findViewById(R.id.tvChatName);
            tvId = itemView.findViewById(R.id.tvIdChat);
            tvContent = itemView.findViewById(R.id.tvLastChat);
            tvTime = itemView.findViewById(R.id.tvChatTime);
            itemView.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    Context context = view.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", tvId.getText().toString());
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("id", bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
