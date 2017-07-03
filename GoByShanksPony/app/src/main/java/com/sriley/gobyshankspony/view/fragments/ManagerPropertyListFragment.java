package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.FirebaseManager;
import com.sriley.gobyshankspony.model.FirebaseManagerListingRecord;
import com.sriley.gobyshankspony.model.ListingProperty;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractManagerListingRecordsListener;
import com.sriley.gobyshankspony.model.interfaces.FirebaseExtractSinglePropertyListener;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;
import com.sriley.gobyshankspony.view.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ManagerPropertyListFragment extends Fragment implements FirebaseExtractManagerListingRecordsListener, FirebaseExtractSinglePropertyListener {



    @BindView(R.id.ManagerlistRecyclerView)RecyclerView mManagerListRecyclerView;
    @BindView(R.id.ManagerListFabMenu)FloatingActionsMenu mFabMenu;
    @BindView(R.id.ManagerListAddPropertyFab)FloatingActionButton mAddPropertyActionButton;


    ArrayList<FirebaseManagerListingRecord> mListingRecords;
    ArrayList<ListingProperty> mProperties=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manager_list,container,false);
        ButterKnife.bind(this,view);
        FirebaseManager.getManagedPropertyRecords(this);
        return view;
        //TODO: Compress images before saving them in a database
    }


    @OnClick(R.id.ManagerListAddPropertyFab)
    public void onAddPropertyFabClicked(){
        mFabMenu.collapse();
        FragmentFactory.startAddPropertyFragment((AppCompatActivity) getActivity());
    }


    @Override
    public void onListingRecordsExtracted(ArrayList<FirebaseManagerListingRecord> listingRecords) {
        mListingRecords=listingRecords;
        extractProperties();
    }


    private void extractProperties(){
        for (FirebaseManagerListingRecord listingRecord:mListingRecords){
            FirebaseManager.getManagedProperty(this,listingRecord);
        }
    }


    @Override
    public void onPropertyExtracted(ListingProperty property) {
        mProperties.add(property);
        if(mProperties.size()==mListingRecords.size()){
            setupRecyclerView();
            //TODO: implement setupRecyclerView
        }
    }

    private void setupRecyclerView(){
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(mProperties, (AppCompatActivity) getActivity());
        mManagerListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mManagerListRecyclerView.setAdapter(adapter);
    }
}
