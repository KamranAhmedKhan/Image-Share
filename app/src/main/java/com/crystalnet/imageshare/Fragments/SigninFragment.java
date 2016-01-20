package com.crystalnet.imageshare.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.Post;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class SigninFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    public SigninFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    EditText email;
    EditText password;
    Button signin_up;
    TextView textView;
    TextView signup_Textview;
    View Root;
    private User user;
    Context c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Root = inflater.inflate(R.layout.fragment_signin, container, false);
        uiInit(Root);

        FirebaseHandler.getInstance().getLoginedUser(new ServiceListener<User,FirebaseError>() {
            @Override
            public void success(User obj) {
                user = obj;
                dowork();
            }

            @Override
            public void error(FirebaseError firebaseError) {
                Utilities.errorToast(firebaseError.toString());
            }
        });

        //Monitoring Firebase Authentication
        if (FirebaseHandler.getInstance().getAuthStatus()) {
            // user is logged in
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit();
                }else{
                    signin();
                    signup();
                }

        return Root;
    }

    public void dowork(){
        Utilities.successToast("Name: " + user.getName() +
                ", Email: " + user.getEmail() + ", Image: " + user.getImage());

    }

    private void uiInit(View Root){
        email = (EditText)Root.findViewById(R.id.email);
        password = (EditText)Root.findViewById(R.id.password);
        signin_up = (Button)Root.findViewById(R.id.email_sign_in_button);
        textView = (TextView)Root.findViewById(R.id.textView);
        signup_Textview = (TextView)Root.findViewById(R.id.signup_TextView);
        c = getActivity();
    }

    public void signin() {
                signin_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().matches("")) {
                            email.setError("Field must be filled");
                        } else if (password.getText().toString().matches("")) {
                            password.setError("Field must be filled");
                        } else {
                            //Login
                            FirebaseHandler.getInstance().firebaseLogin(email.getText().toString(), password.getText().toString(), new ServiceListener<AuthData, FirebaseError>() {
                                @Override
                                public void success(AuthData obj) {
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.container, new HomeFragment())
                                            .commit();
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
    public void signup() {
                signup_Textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new Signup_Fragment()).addToBackStack(null)
                                .commit();
                    }
                });
            }

    public interface OnFragmentInteractionListener {
                // TODO: Update argument type and name
                public void onFragmentInteraction(Uri uri);
            }

}
