package com.crystalnet.imageshare.Fragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Handlers.CloudinaryHandler;
import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.ListAdapter;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
    private int PICK_IMAGE = 1;
    Uri outputFileUri;


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
    ArrayList<Post> list;
    ListView listView;
    ListAdapter adapter;
    String result;
    Map uploadResult;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_home, container, false);
        try {

            initWidgets(V);
        } catch (NullPointerException e) {
            Utilities.errorToast("Fetching!");
        }
        FloatingActionButton fab = (FloatingActionButton) V.findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 0);
                // Determine Uri of camera image to save.
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.container, new NewPostFragment()).addToBackStack(null).commit();
            }
        });

        listViewListener();//extra func
        return V;
    }


    private void initWidgets(View v) {
        listView = (ListView) v.findViewById(R.id.listView);
        list = new ArrayList<Post>();
        adapter = new ListAdapter(getActivity(), 0, list);
        listView.setAdapter(adapter);
        Firebase refresh = FirebaseHandler.getInstance().getLoginedUserNode();
        refresh.keepSynced(true);


        FirebaseHandler.getInstance().getPosts(new ServiceListener<Post, FirebaseError>() {
            @Override
            public void success(Post obj) {
                list.add(obj);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void error(FirebaseError obj) {
                Utilities.errorToast("Feed Fetching Error: " + obj.getMessage());
            }
        });

    }

    private void listViewListener() {

    }
}
