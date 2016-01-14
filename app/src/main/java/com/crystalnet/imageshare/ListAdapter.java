package com.crystalnet.imageshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 05/01/16.
 */
public class ListAdapter extends ArrayAdapter{
    ImageView post_imageView;
    ImageButton share_Button;
    TextView post_name;
    TextView post_msg;
    TextView post_time;
    TextView post_date;
    LayoutInflater layoutInflater;

    public ListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.card_view_layout, null);
        }

        share_Button = (ImageButton)convertView.findViewById(R.id.share);
        post_imageView = (ImageView)convertView.findViewById(R.id.post_imageView);
        post_name = (TextView)convertView.findViewById(R.id.post_name);
        post_msg = (TextView)convertView.findViewById(R.id.post_msg);
//        post_time = (TextView)convertView.findViewById(R.id.post_time);
//        post_date = (TextView)convertView.findViewById(R.id.post_date);

        Post p = (Post)getItem(position);
        post_name.setText(p.getName());
        post_msg.setText(p.getMsg());
//        post_time.setText(p.time);
//        post_date.setText(p.date);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
