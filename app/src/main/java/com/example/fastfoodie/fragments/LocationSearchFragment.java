package com.example.fastfoodie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fastfoodie.R;
import com.example.fastfoodie.models.FirebaseUserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationSearchFragment extends Fragment {
    private ImageView mProfileImage;


    public LocationSearchFragment() {
        // Required empty public constructor
    }

    public static LocationSearchFragment newInstance() {
        return new LocationSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUserModel userModel = FirebaseUserModel.getUserInstanceModel();
        View root = inflater.inflate(R.layout.fragment_location_search, container, false);
        mProfileImage = root.findViewById(R.id.user_profile_btn);
        Glide.with(this).load("https://i.pinimg.com/originals/ca/76/0b/ca760b70976b52578da88e06973af542.jpg").into(mProfileImage);
        return root;
    }
}