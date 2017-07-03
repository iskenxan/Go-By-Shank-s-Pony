package com.sriley.gobyshankspony.view.adapters;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.interfaces.FirebasePropertyDeleteListener;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.model.utils.GlideManager;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<ListingProperty> mProperties;
    AppCompatActivity mActivity;


    public RecyclerViewAdapter(ArrayList<ListingProperty> properties,AppCompatActivity activity){
        mProperties=properties;
        mActivity=activity;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_recycler_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(position);
    }


    @Override
    public int getItemCount() {
        return mProperties.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, FirebasePropertyDeleteListener {
        TextView mNameTextView;
        TextView mAddressTextView;
        TextView mOptionsTextView;

        ImageView mImageView;

        int mPosition;


        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView= (TextView) itemView.findViewById(R.id.ManagerListItemName);
            mAddressTextView= (TextView) itemView.findViewById(R.id.ManagerListItemAddress);
            mImageView= (ImageView) itemView.findViewById(R.id.ManagerListItemImageView);
            mOptionsTextView= (TextView) itemView.findViewById(R.id.ManagerListItemOptionsTextView);

            mOptionsTextView.setOnClickListener(this);
        }



        public void bind(int position) {
            ListingProperty property=mProperties.get(position);
            mPosition=position;
            mNameTextView.setText(property.getName());
            String fullAddress=property.getAddress()+","+property.getCity()+" "+property.getState();
            mAddressTextView.setText(fullAddress);
            loadImage(property);
        }


        private void loadImage(ListingProperty property){
            if(property.getImageUrl()!=null&&!property.getImageUrl().equals(""))
                GlideManager.loadRoundedProfilePicture(mImageView,property.getImageUrl());
            else
               GlideManager.loadRoundedProfilePicture(mImageView,R.drawable.image_not_available);
        }


        @Override
        public void onClick(View view) {
            PopupMenu popup = new PopupMenu(mOptionsTextView.getContext(),mOptionsTextView);
            popup.inflate(R.menu.listing_item_menu);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    handleSelectedMenuItem(item);
                    return false;
            }});

            popup.show();
        }



        private void handleSelectedMenuItem(MenuItem item){
            ListingProperty property=mProperties.get(mPosition);
            switch (item.getItemId()) {
                case R.id.ListingMenuEdit:{
                    FragmentFactory.startEditPropertyFragment(mActivity,property);
                    break;
                }
                case R.id.ListingMenuDelete:{
                    FirebaseManager.removeListingPropertyFromDatabase(property,ViewHolder.this);
                    break;
                }

            }
        }



        @Override
        public void onPropertyRemoved(boolean success) {
            if(success){
                Toast.makeText(mOptionsTextView.getContext(),"Listing was successfully deleted from the database", Toast.LENGTH_LONG).show();
                FragmentFactory.startManagerPropertyListFragment(mActivity);
            }
        }


    }
}
