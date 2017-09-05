package com.example.lazyfragmentdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

    private List<String> mPhotos;


    @Override
    public MyAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ItemViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext()).load(mPhotos.get(position)).into(holder.mImg);
        GlideApp.with(holder.itemView.getContext()).load(mPhotos.get(position)).into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.mImg);
        }
    }

    public void addData(List<String> mPhotos){
        this.mPhotos = mPhotos;
        notifyDataSetChanged();
    }
}
