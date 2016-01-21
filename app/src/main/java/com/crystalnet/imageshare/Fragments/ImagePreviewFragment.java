package com.crystalnet.imageshare.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePreviewFragment extends Fragment {

Post post;
    public ImagePreviewFragment() {
        // Required empty public constructor
    }

View V;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_image_preview, container, false);

Utilities.successToast(post.getMsg());

        return V;
    }


}
