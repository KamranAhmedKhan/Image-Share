package com.crystalnet.imageshare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.Utils.Utilities;

import java.util.ArrayList;

/**
 * Created by root on 05/01/16.
 */
public class ProfileGridAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    ImageView post_ImageView;
    Post post;

    public ProfileGridAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_result_item, null);
        }

        post_ImageView = (ImageView)convertView.findViewById(R.id.p_image);

//        post = (Post) getItem(position);
Log.e("Grid: ",getItem(position)+"");
        Utilities.renderImage(String.valueOf(getItem(position)), post_ImageView);
notifyDataSetChanged();
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
