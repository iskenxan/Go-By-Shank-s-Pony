package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractPropertiesListener;
import com.sriley.gobyshankspony.model.utils.Formatter;
import com.sriley.gobyshankspony.view.adapters.ListingViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment implements FirebaseExtractPropertiesListener {

    @BindView(R.id.favoritesViewPager)ViewPager mViewPager;
    @BindView(R.id.favoritesEmptyTextContainer)LinearLayout mEmptyContainer;


    ArrayList<ListingProperty> mPropertyList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorites,container,false);
        ButterKnife.bind(this,view);
        FirebaseManager.getFavorites(this);

        setActionBarTitle("FAVORITES");

        return view;
    }


    @Override
    public void onPropertiesExtracted(ArrayList<ListingProperty> favorites) {
        mPropertyList=favorites;
        if(favorites.size()>0){
            handleResult();
        }
    }


    public  void handleResult(){
        try {
            mEmptyContainer.setVisibility(View.INVISIBLE);
            setupViewPager();
        }
        catch (Exception e){
            //Some weird exception happens here sometimes
        }
    }



    private void setupViewPager(){
        Formatter.removePropertyDuplicates(mPropertyList);
        if(mViewPager.getAdapter()!=null)
            mViewPager.getAdapter().notifyDataSetChanged();
        else{
            ListingViewPagerAdapter adapter=new ListingViewPagerAdapter(getChildFragmentManager(), mPropertyList,ListingViewPagerAdapter.FRAGMENT_TYPE_FAVORITE);
            mViewPager.setAdapter(adapter);
        }
    }


    private void setActionBarTitle(String title){
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(title);
    }


}
