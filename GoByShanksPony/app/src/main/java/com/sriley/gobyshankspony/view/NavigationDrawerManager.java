package com.sriley.gobyshankspony.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import com.sriley.gobyshankspony.MainActivity;
import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.model.utils.FragmentFactory;


public class NavigationDrawerManager implements AdapterView.OnItemClickListener {
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private AppCompatActivity mActivity;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;


    public NavigationDrawerManager(AppCompatActivity activity){
        mActivity=activity;
        mDrawerList= (ListView) mActivity.findViewById(R.id.navList);
        mDrawerLayout= (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
    }


    public void onConfigurationChanged(Configuration newConfig){
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public boolean onOptionsSelected(MenuItem item){
        int id = item.getItemId();


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else
            return false;
    }



    public void syncToggleState(){
        mDrawerToggle.syncState();
    }


    public void setupDrawer(){
        addDrawerItems();
        setupDrawerToggle();
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
    }



    private void addDrawerItems() {
        String[] osArray = { "Home","Favorites","Logout" };
        mAdapter = new ArrayAdapter<String>(mActivity,android.R.layout.simple_list_item_1,osArray){
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



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mDrawerLayout.closeDrawers();
        if(position==0){
            FragmentFactory.startSearchResultFragment(mActivity,1);
        }

        else if(position==1){
            FragmentFactory.startFavoritesFragment(mActivity);

        }
        else if(position==2){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(mActivity,MainActivity.class);
            mActivity.startActivity(intent);
        }
    }





    private void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActivity.invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mActivity.invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


}
