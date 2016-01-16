package com.crystalnet.imageshare.Fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.Handlers.CloudinaryHandler;
import com.crystalnet.imageshare.ListAdapter;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View V;
    ListView listView;
    ListAdapter adapter;
    String result;Map uploadResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_home, container, false);

        initWidgets(V);


        FloatingActionButton fab = (FloatingActionButton) V.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        return V;
    }

    private void initWidgets(View v) {
        listView = (ListView) v.findViewById(R.id.listView);
        ArrayList<Post> postArrayList = new ArrayList<Post>();
        postArrayList.add(Post.dummyPost);
        postArrayList.add(Post.dummyPost);


        /*final Cloudinary cloudinary = new Cloudinary("cloudinary://573468729839527:k8qo3_8Ex_kPh-M9qkB8BJ3BxyQ@kamran");
        new Thread(new Runnable() {
            public void run() {
                try {
                    //uploadResult = cloudinary.uploader().upload("http://i.imgur.com/AITuone.png",ObjectUtils.emptyMap());//its working
                    @SuppressWarnings("ResourceType")
                    InputStream ins = getResources().openRawResource(R.drawable.aa);
//                    BufferedReader br = new BufferedReader(new InputStreamReader(ins));
//                    StringBuffer sb;
//                    String line;
//                    while((line = br.readLine()) != null){
//                        sb.append(line);
//                    }
//
//                    File f = new File(sb.toString());

                    uploadResult = cloudinary.uploader().upload(ins,ObjectUtils.emptyMap());
                    result = uploadResult.get("public_id").toString();
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),(String)uploadResult.get("public_id"),Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Utilities.successToast(e.toString());
                }
            }
        }).start();*/




        adapter = new ListAdapter(getActivity(), 0, postArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Post p = (Post)parent.getItemAtPosition(position);
                        p.*/
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            adapter.add(Post.dummyPost);
//            File file = new File(BitmapFactory.);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                if(inputStream==null)Toast.makeText(getActivity(), "input stream in null", Toast.LENGTH_SHORT).show();
                //There will saving inpustream to cloudinary
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity(), "Picture Saved", Toast.LENGTH_SHORT).show();
        }
//        iv.setImageBitmap(bp);
    }
}
