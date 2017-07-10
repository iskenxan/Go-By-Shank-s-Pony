package com.sriley.gobyshankspony.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sriley.gobyshankspony.R;
import com.sriley.gobyshankspony.view.adapters.GalleryRecyclerViewAdapter;

import java.util.ArrayList;


public class GalleryDialog extends DialogFragment implements View.OnClickListener {

    RecyclerView mGalleryRecyclerView;
    Button mCloseButton;

    ArrayList<String> mImageUrls=new ArrayList<>();


    public static void displayDialog(FragmentManager fragmentManager, ArrayList<String> imageUrls){
        GalleryDialog galleryDialog = GalleryDialog.newInstance(imageUrls);
        galleryDialog.show(fragmentManager,"gallery_dialog");
    }


    public GalleryDialog() {

    }


    public static GalleryDialog newInstance(ArrayList<String> imageUrls) {
        GalleryDialog frag = new GalleryDialog();
        Bundle args = new Bundle();
        args.putStringArrayList("urls", imageUrls);
        frag.setArguments(args);
        return frag;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGalleryRecyclerView= (RecyclerView) view.findViewById(R.id.GalleryRecycleView);
        mCloseButton= (Button) view.findViewById(R.id.GalleryCloseButton);

        mImageUrls=getArguments().getStringArrayList("urls");
        GalleryRecyclerViewAdapter adapter=new GalleryRecyclerViewAdapter(mImageUrls);
        mGalleryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGalleryRecyclerView.setAdapter(adapter);

        mCloseButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        dismiss();
    }


}
