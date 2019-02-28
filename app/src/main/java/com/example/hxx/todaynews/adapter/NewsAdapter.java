package com.example.hxx.todaynews.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hxx.todaynews.R;
import com.example.hxx.todaynews.news.News;
import com.example.hxx.todaynews.tools.MyImageView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    public List<News.Newslist > mlist;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView description;
        View newsview;
        MyImageView imageView;

        public ViewHolder(View view){
            super(view);

            imageView=(MyImageView) view.findViewById(R.id.image);

            textView=(TextView) view.findViewById(R.id.title);
            description=(TextView) view.findViewById(R.id.description);
        }

    }
    public NewsAdapter(List<News.Newslist> newslists){
        mlist=newslists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        News.Newslist newslist=mlist.get(i);
        viewHolder.textView.setText(newslist.getTitle());
        viewHolder.description.setText(newslist.getDescription());
        viewHolder.imageView.setImageURL(newslist.getPicUrl());
        Log.e("MainActivity",viewHolder.textView.getText().toString());
        View itemView = ((LinearLayout) viewHolder.itemView).getChildAt(0);

        if (mOnItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
