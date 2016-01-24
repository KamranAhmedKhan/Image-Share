package com.crystalnet.imageshare.Fragments;

import android.app.Activity;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.ProfileGridAdapter;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ProfileViewFragment extends Fragment {

    private View V;
    public static String id;
    private ImageView s_img;
    private TextView s_name;
    private TextView s_details;
    private TextView s_emial;
    private TextView s_photos;
    private TextView s_following;
    private TextView s_followers;
    GridView gridView;
    ProfileGridAdapter adapter;
    private ArrayList<String> list;
    private Button follow_button;
    private String s_email;
    private String l_email;

    public ProfileViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_profile_view, container, false);
        init();
        followButton();

        return V;
    }

    private void followButton() {
        follow_button = (Button)V.findViewById(R.id.followButton);
        FirebaseHandler.getInstance().getLoginedUserNode().child("following").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

                FirebaseHandler.getInstance().getUserById(id, new ServiceListener<DataSnapshot, FirebaseError>() {
                    @Override
                    public void success(DataSnapshot obj) {
                        User user = obj.getValue(User.class);
                        s_email = user.getEmail();
                        if (dataSnapshot.getValue().toString().matches(s_email)){
                            follow_button.setText("Following");
                        }
                    }
                    @Override
                    public void error(FirebaseError obj) {

                    }
                });
//                searchedUser.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot Snapshot) {
//                        searchedUser_email = Snapshot.getValue().toString();
//                        if (dataSnapshot.getValue().toString().matches(searchedUser_email)){
//                            follow_button.setText("Following");
//                        } } @Override public void onCancelled(FirebaseError firebaseError) {} });
            }
            @Override            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow_button.getText().toString().matches("Follow")) {
                    FirebaseHandler.getInstance().getLoginedUserNode().child("following").child(id).setValue(s_email);
                    follow_button.setText("Following");
                    Utilities.successToast("Now you are Following");

                    FirebaseHandler.getInstance().getLoginedUser(new ServiceListener<User, FirebaseError>() {
                        @Override
                        public void success(User obj) {
                            l_email = obj.getEmail();
                        }
                        @Override
                        public void error(FirebaseError obj) {

                        }
                    });
                    FirebaseHandler.getInstance().getSearchedUserNode(id).child("followers")
                            .child(FirebaseHandler.firebaseRef.getAuth().getUid()).setValue(l_email);
                }
                else if(follow_button.getText().toString().matches("Following")){

                    FirebaseHandler.getInstance().getLoginedUserNode().child("following").child(id).setValue(null);
                    follow_button.setText("Follow");
                    Utilities.successToast("Now you are not Following");

                    FirebaseHandler.getInstance().getSearchedUserNode(id).child("followers")
                            .child(FirebaseHandler.firebaseRef.getAuth().getUid()).setValue(null);
                }
            }
        });
    }

    private void init() {
        s_img = (ImageView) V.findViewById(R.id.s_img);
        s_name = (TextView) V.findViewById(R.id.s_name);
        s_details = (TextView) V.findViewById(R.id.s_details);
        s_emial = (TextView) V.findViewById(R.id.s_email);
        s_photos = (TextView) V.findViewById(R.id.s_photos);
        s_following = (TextView) V.findViewById(R.id.s_following);
        s_followers = (TextView) V.findViewById(R.id.s_followers);
        gridView = (GridView) V.findViewById(R.id.gridView);
        list = new ArrayList<String>();
        adapter = new ProfileGridAdapter(getActivity(), 0, list);
        gridView.setAdapter(adapter);

        FirebaseHandler.getInstance().getUserById(id, new ServiceListener<DataSnapshot, FirebaseError>() {
            @Override
            public void success(DataSnapshot obj) {
                Log.e("Prifile View Frag", obj.toString());
                User user = obj.getValue(User.class);
                s_name.setText(user.getName());
                s_photos.setText(String.valueOf(obj.child("Posts").getChildrenCount()));
                s_followers.setText(String.valueOf(obj.child("followers").getChildrenCount()));
                s_following.setText(String.valueOf(obj.child("following").getChildrenCount()));
                s_details.setText(user.getAbout());
                s_emial.setText(user.getEmail());
                Utilities.renderImage(user.getImage(), s_img);

                obj.child("Posts").getRef().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        Utilities.successToast(dataSnapshot.getValue().toString());

                        FirebaseHandler.getInstance().getSearchedPostNode(id, dataSnapshot.getValue().toString())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Post post = dataSnapshot.getValue(Post.class);
                                list.add(post.getImage());
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
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
            }

            @Override
            public void error(FirebaseError obj) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        follow_button.setText("Follow");
    }
}
