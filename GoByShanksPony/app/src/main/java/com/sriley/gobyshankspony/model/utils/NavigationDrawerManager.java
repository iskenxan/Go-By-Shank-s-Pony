package com.sriley.gobyshankspony.model.utils;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sriley.gobyshankspony.LoginActivity;
import com.sriley.gobyshankspony.R;


public class NavigationDrawerManager implements AdapterView.OnItemClickListener {
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private AppCompatActivity mActivity;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private String mUserType;


    public NavigationDrawerManager(AppCompatActivity activity){
        mActivity=activity;
        mDrawerList= (ListView) mActivity.findViewById(R.id.navList);
        mDrawerLayout= (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
    }


    public void onConfigurationChanged(Configuration newConfig){
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public boolean onOptionsSelected(MenuItem item){

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else
            return false;
    }



    public void syncToggleState(){
        if(mDrawerToggle!=null)
            mDrawerToggle.syncState();
    }


    public void setupDrawer(String userType){
        mUserType=userType;
        addDrawerItems();
    }



    private void addDrawerItems() {

        mAdapter = new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1,getMenuArray()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(mActivity.getResources().getColor(android.R.color.white));
                return textView;
            }
        };
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(this);
    }


    private String[] getMenuArray(){
        String[] menuItems;
        if(isRenterOrBroker())
            menuItems=new String[]{ "Search","Favorites","Logout" };
        else
            menuItems=new String[]{"Manage Properties","Logout"};

        return menuItems;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mDrawerLayout.closeDrawers();
        if(position==0){
            if(isRenterOrBroker())
                FragmentFactory.startSearchResultFragment(mActivity,1);
            else
                FragmentFactory.startManagerPropertyListFragment(mActivity);
        }

        else if(position==1){
            if(isRenterOrBroker())
                FragmentFactory.startFavoritesFragment(mActivity);
            else
                logout();
        }
        else if(position==2){
          logout();
        }
    }


    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(mActivity,LoginActivity.class);
        mActivity.startActivity(intent);
    }


    private boolean isRenterOrBroker(){
        return mUserType.equals(Formatter.USERTYPE_AGENT)||mUserType.equals(Formatter.USERTYPE_RENTER);
    }




    private void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActivity.invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mActivity.invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


}
