package com.crystalnet.imageshare.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.SearchListAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ListView searchListView;
    View V;
    SearchListAdapter adapter;
    ArrayList<User> list;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_search, container, false);
        init();

        return V;
    }

    private void init() {
        searchListView = (ListView)V.findViewById(R.id.searchListView);
        list = new ArrayList<User>();
        FirebaseHandler.firebaseRef.child("Users").
        list.add(User.dummyUser);
        adapter = new SearchListAdapter(getActivity(), 0, list);
        searchListView.setAdapter(adapter);
    }


}
