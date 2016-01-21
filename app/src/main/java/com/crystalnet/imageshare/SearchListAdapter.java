package com.crystalnet.imageshare;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Fragments.ImagePreviewFragment;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.Utils.Utilities;

import java.util.ArrayList;

/**
 * Created by root on 05/01/16.
 */
public class SearchListAdapter extends ArrayAdapter {
    ImageView post_profileImageView;
    TextView post_name;
    TextView post_email;
    LayoutInflater layoutInflater;
    User user;

    public SearchListAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_result_item, null);
        }

        post_profileImageView = (ImageView)convertView.findViewById(R.id.search_image);

        post_name = (TextView) convertView.findViewById(R.id.search_name);
        post_email = (TextView) convertView.findViewById(R.id.search_email);

        user = (User) getItem(position);
        post_name.setText(user.getName());
        post_email.setText(user.getEmail());
        Utilities.renderImage(user.getImage(), post_profileImageView);

//        post_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Post post = (Post) getItem(position);
//                ((FragmentActivity) Utilities.context).getFragmentManager().beginTransaction()
//                        .replace(R.id.container, new ImagePreviewFragment()).addToBackStack(null).commit();
//
//
//            }
//        });

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
