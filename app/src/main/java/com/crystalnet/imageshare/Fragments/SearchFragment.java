package com.crystalnet.imageshare.Fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.SearchListAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ListView searchListView;
    View V;
    SearchListAdapter adapter;
    ArrayList<User> list;
    ArrayList<String> ids;
    private EditText inputSearch;


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
        inputSearch = (EditText)V.findViewById(R.id.searchEditText);
        searchListView = (ListView) V.findViewById(R.id.searchListView);
        list = new ArrayList<User>();
        ids = new ArrayList<String>();
        adapter = new SearchListAdapter(getActivity(), 0, list, ids);
        searchListView.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        FirebaseHandler.firebaseRef.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ids.add(dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                list.add(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("User Id: ", ids.get(i));
                ProfileViewFragment.id = ids.get(i);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                fm.beginTransaction().replace(R.id.container, new ProfileViewFragment()).addToBackStack(null).commit();
            }
        });

    }


}
