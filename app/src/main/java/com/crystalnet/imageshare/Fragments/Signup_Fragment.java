package com.crystalnet.imageshare.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Signup_Fragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// */
public class Signup_Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Signup_Fragment() {
        // Required empty public constructor
    }


    Firebase firebase;
    Firebase firebase_user;
    EditText name;
    EditText email;
    EditText password;
    Button sign_up;
    TextView textView;
    Context c;
    View Root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Root = inflater.inflate(R.layout.fragment_signup, container, false);

        init(Root);
        signup();
        signin();
        return Root;
    }

    private void init(View Root) {
        firebase = FirebaseHandler.firebaseRef;
        firebase_user = firebase.child("Users");
        name = (EditText) Root.findViewById(R.id.signup_name);
        email = (EditText) Root.findViewById(R.id.signup_email);
        password = (EditText) Root.findViewById(R.id.signup_password);
        sign_up = (Button) Root.findViewById(R.id.email_sign_up_button);
        textView = (TextView) Root.findViewById(R.id.textView);
        c = getActivity();
    }


    private void signup() {
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.getText().toString().matches("")) {
                    email.setError("Field must be filled");
                } else if (password.getText().toString().matches("")) {
                    password.setError("Field must be filled");
                } else {
                    //SignUp
                    FirebaseHandler.getInstance().firebaseCreateUser(email.getText().toString(), password.getText().toString(), name.getText().toString()
                            , new ServiceListener<Map<String, Object>, FirebaseError>() {
                        @Override
                        public void success(Map<String, Object> obj) {
                            textView.setText("Successfully created user account with uid: " + obj.get("uid"));
                            firebase.child("UsersList").child(obj.get("uid").toString()).setValue(email.getText().toString());

                            //Login
                            firebase.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    Map<String, String> newuser = new HashMap<String, String>();
                                    newuser.put("email", authData.getProviderData().get("email").toString());
                                    newuser.put("provider", authData.getProvider());
                                    newuser.put("name", name.getText().toString());
                                    newuser.put("image", "default image");
                                    newuser.put("about", "No information provided Yet");
                                    newuser.put("age", "No information provided Yet");
                                    newuser.put("location", "No information provided Yet");
                                    firebase_user.child(authData.getUid()).setValue(newuser);

                                    //If login successfull GOTO EditProfile Fragment......
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.container, new HomeFragment()).commit();
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.container, new EditProfileFragment()).addToBackStack("Home")
                                            .commit();
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    // there was an error in login
                                    textView.setText(firebaseError.toString());
                                }
                            });
                        }

                        @Override
                        public void error(FirebaseError obj) {
                            textView.setText(obj.toString());
                        }
                    });
                }
            }
        });
    }

    //Create Account Clickable Text
    private void signin() {
        TextView signup_TextView = (TextView) Root.findViewById(R.id.signup_TextView);
        signup_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SigninFragment())
                        .commit();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.toolbar.setVisibility(View.VISIBLE);
    }
}
