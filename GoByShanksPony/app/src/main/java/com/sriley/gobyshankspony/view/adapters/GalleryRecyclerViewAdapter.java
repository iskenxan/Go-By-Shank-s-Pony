package com.sriley.gobyshankspony.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sriley.gobyshankspony.R;

import java.util.ArrayList;


public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<String> mImageUrls;

    public GalleryRecyclerViewAdapter(ArrayList<String> imageUrls){
        mImageUrls=imageUrls;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler_item,parent,false);
        return new GalleryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(position);
    }



    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.GalleryItemImageView);
        }


        private void bind(int position){
            String url=mImageUrls.get(position);
            Picasso.with(mImageView.getContext()).load(url).into(mImageView);
        }


    }



}
