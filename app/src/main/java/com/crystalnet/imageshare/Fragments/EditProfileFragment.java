package com.crystalnet.imageshare.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.ProfileAsync;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {


    private View V;
    private ImageView edit_imageView;
    public static EditText edit_age;
    public static EditText edit_location;
    public static EditText edit_about;
    private Button edit_button;
    private String picturePath;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        init();
        SaveButton();

        return V;
    }

    private void SaveButton() {
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_age.getText().toString().matches("")) {
                    edit_age.setError("Can't be null");
                }else if(edit_location.getText().toString().matches("")) {
                    edit_location.setError("Can't be null");
                }else if(edit_about.getText().toString().matches("")) {
                    edit_about.setError("Can't be null");
                }else {
                    ProfileAsync async = new ProfileAsync();
                    async.execute(picturePath);
                    Utilities.successToast("Profile Updated:\nYou will see changes after restart Application...");
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    private void init() {
        edit_imageView = (ImageView)V.findViewById(R.id.edit_imageView);
        edit_age = (EditText)V.findViewById(R.id.edit_age);
        edit_location = (EditText)V.findViewById(R.id.edit_location);
        edit_about = (EditText)V.findViewById(R.id.edit_about);
        edit_button = (Button)V.findViewById(R.id.edit_button);
        edit_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = data.getData();
        if (resultCode == Activity.RESULT_OK&&requestCode==1) {
            try {
                selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                picturePath = cursor.getString(columnIndex);
                cursor.close();

                edit_imageView.setImageURI(selectedImageUri);
            } catch (Exception e) {

            }
        }else Utilities.errorToast("Problem in getting Image from Gallery");
    }
}
