package com.crystalnet.imageshare.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePreviewFragment extends Fragment {

public static Post post;
    private ImageView post_imageView;
    private TextView post_name;
    private TextView post_msg;
    private ImageButton share_Button;

    public ImagePreviewFragment() {
        // Required empty public constructor
    }

View V;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_image_preview, container, false);
        post_imageView = (ImageView) V.findViewById(R.id.postImage);
        share_Button = (ImageButton) V.findViewById(R.id.share1);
        post_name = (TextView) V.findViewById(R.id.userName);
        post_msg = (TextView) V.findViewById(R.id.imageDetails);
        post_name.setText(post.getName());
        post_msg.setText(post.getMsg());
        Utilities.renderImage(post.getImage(), post_imageView);
//Utilities.successToast(post.getMsg());
        share_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ImagePreviewData: ","Name: " + post.getName() +
                        " \nMsg: " + post.getMsg() +
                        " \nImage: " + post.getImage());
            }
        });

        return V;
    }


}
