package com.example.phanv.camera.Model.CommentModel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ProductView.ListProductAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by phanv on 14-Dec-17.
 */

public class CommnetAdapter extends RecyclerView.Adapter<CommnetAdapter.ViewComment> {
    private List<Comment> list;
    Activity activity;

    public CommnetAdapter(List<Comment> list,Activity activity) {
        this.list = list;
        this.activity=activity;
    }

    @Override
    public CommnetAdapter.ViewComment onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v= inflater.inflate(R.layout.comment, parent, false);
        return new CommnetAdapter.ViewComment(v);
    }

    @Override
    public void onBindViewHolder(CommnetAdapter.ViewComment holder, int position) {
        Comment comment= list.get(position);
        if(comment.getImg().length()>40){
            Picasso.with(activity).load(comment.getImg()).into(holder.img);
        }
        else {
            holder.img.setImageResource(R.drawable.img_account);
        }
        holder.rtb.setRating(Float.parseFloat(comment.getStart()));
        holder.tvName.setText(comment.getName());
        holder.tvContent.setText(comment.getContent());
        holder.tvDate.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewComment extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvContent;
        CircleImageView img;
        RatingBar rtb;
        TextView tvDate;
        public ViewComment(View itemView) {
            super(itemView);
            tvContent= itemView.findViewById(R.id.tvContentComment);
            tvName=itemView.findViewById(R.id.tvNameComment);
            img=itemView.findViewById(R.id.imgComment);
            rtb=itemView.findViewById(R.id.rtbAccountComment);
            tvDate=itemView.findViewById(R.id.tvDateComment);
        }
    }
}
