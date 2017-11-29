package com.example.phanv.mydiary.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phanv.mydiary.Activity.ListDiaryActivity;
import com.example.phanv.mydiary.Activity.ViewDiary;
import com.example.phanv.mydiary.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by phanv on 02-Nov-17.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewDiary> implements Filterable {
    public List<Diary> listDiary;
    public List<Diary> listDiaryCopy;
    public Activity activity;
    public DiaryAdapter(List<Diary> listDiary, Activity activity) {
        this.listDiary = listDiary;
        this.activity = activity;
        this.listDiaryCopy = listDiary;
    }

    @Override
    public ViewDiary onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary,parent,false);
        return new ViewDiary(itemView);
    }

    @Override
    public void onBindViewHolder(final DiaryAdapter.ViewDiary holder, final int position) {
        final Diary diary = listDiary.get(position);
        String filePath = diary.getUrl();
        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
        if(filePath.length()<5)
        {
            holder.imgDiary.setImageResource(R.drawable.diary_icon_final);
        }
        else
        {
            holder.imgDiary.setImageBitmap(yourSelectedImage);
        }
        holder.tvDate.setText(diary.getDate());
        holder.tvTitle.setText(diary.getTitle());
        holder.rtBar.setRating(diary.getLike());
    }

    @Override
    public int getItemCount() {
        return listDiary.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchContent = charSequence.toString();
                if(searchContent.isEmpty()) {
                    listDiary = listDiaryCopy;
                }
                else {
                    ArrayList<Diary> list = new ArrayList<>();
                    for (Diary diary : listDiary) {
                        String like=diary.getLike()+"";
                        if (diary.getDate().toLowerCase().contains(searchContent) || diary.getTitle().toLowerCase().contains(searchContent)
                                || like.contains(searchContent))
                            list.add(diary);
                    }
                    listDiary = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listDiary;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listDiary = (ArrayList<Diary>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class ViewDiary extends RecyclerView.ViewHolder{
        public ImageView imgDiary;
        public TextView tvDate;
        public TextView tvTitle;
        public RatingBar rtBar;

        public ViewDiary(View v) {
            super(v);
            imgDiary = (ImageView) v.findViewById(R.id.imgDiary);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            rtBar = (RatingBar) v.findViewById(R.id.rtBarView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle =new Bundle();
                    bundle.putString("date",tvDate.getText().toString());
                    Context context = v.getContext();
                    Intent intent = new Intent(context, com.example.phanv.mydiary.Activity.ViewDiary.class);
                    intent.putExtra("date",bundle);
                    context.startActivity(intent);
                }
            });
        }
    }


}
