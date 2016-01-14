package com.crystalnet.imageshare.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystalnet.imageshare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    public MyProfileFragment() {
        // Required empty public constructor
    }

    View V;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_my_profile, container, false);


        return V;
    }


}
