package com.sriley.gobyshankspony.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ManagerPropertyListFragment extends Fragment {



    @BindView(R.id.ManagerlistRecyclerView)RecyclerView mManagerListRecyclerView;
    @BindView(R.id.ManagerListFabMenu)FloatingActionsMenu mFabMenu;
    @BindView(R.id.ManagerListAddPropertyFab)FloatingActionButton mAddPropertyActionButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_manager_list,container,false);
        ButterKnife.bind(this,view);

        return view;
    }


    @OnClick(R.id.ManagerListAddPropertyFab)
    public void onAddPropertyFabClicked(){
        mFabMenu.collapse();
        FragmentFactory.startAddPropertyFragment((AppCompatActivity) getActivity());
    }

}
