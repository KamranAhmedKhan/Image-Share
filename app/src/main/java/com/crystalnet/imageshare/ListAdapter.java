package com.crystalnet.imageshare;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Fragments.HomeFragment;
import com.crystalnet.imageshare.Fragments.ImagePreviewFragment;
import com.crystalnet.imageshare.Fragments.SigninFragment;
import com.crystalnet.imageshare.Handlers.CloudinaryHandler;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 05/01/16.
 */
public class ListAdapter extends ArrayAdapter {
    ImageView post_profileImageView;
    ImageView post_imageView;
    ImageButton share_Button;
    TextView post_name;
    TextView post_msg;
    TextView post_time;
    TextView post_date;
    LayoutInflater layoutInflater;
    Post p;

    public ListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.card_view_layout, null);
        }

        share_Button = (ImageButton) convertView.findViewById(R.id.share);
        post_profileImageView = (ImageView)convertView.findViewById(R.id.post_profileImageView);
        post_imageView = (ImageView) convertView.findViewById(R.id.post_imageView);
        post_name = (TextView) convertView.findViewById(R.id.post_name);
        post_msg = (TextView) convertView.findViewById(R.id.post_msg);
        post_time = (TextView) convertView.findViewById(R.id.post_time);
        post_date = (TextView) convertView.findViewById(R.id.post_date);

        p = (Post) getItem(position);
        post_name.setText(p.getName());
        post_msg.setText(p.getMsg());
        post_time.setText(p.getTime());
        post_date.setText(p.getDate());
        Utilities.renderImage(p.getImage(), post_imageView);

        post_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = (Post) getItem(position);
                ((FragmentActivity) Utilities.context).getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ImagePreviewFragment()).addToBackStack(null).commit();


            }
        });
        share_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Position: ", position + " \nName: " + ((Post) getItem(position)).getName() +
                        " \nMsg: " + ((Post) getItem(position)).getMsg() +
                        " \nImage: " + ((Post) getItem(position)).getImage());
            }
        });

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
