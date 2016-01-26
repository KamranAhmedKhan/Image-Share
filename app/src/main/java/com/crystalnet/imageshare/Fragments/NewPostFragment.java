package com.crystalnet.imageshare.Fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crystalnet.imageshare.Activities.MainActivity;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.Utils.Async;
import com.crystalnet.imageshare.Utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewPostFragment extends Fragment {

    ImageView pickImageView;
    Button publish;
    public static EditText desc;String picturePath;

    public NewPostFragment() {
        // Required empty public constructor
    }

    View V;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_new_post, container, false);
        init(V);
        PickImage();
        PublishButton();


        return V;
    }

    private void PublishButton() {
//        ((BitmapDrawable) (pickImageView.getDrawable())).getBitmap();
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!desc.getText().toString().matches("")) {
                    Async async = new Async();
                    async.execute(picturePath);
                    Utilities.successToast("Uploading Image...");
                    getFragmentManager().popBackStack();
                }else desc.setError("Description can't be null");
            }
        });


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


    }

    private void init(View v) {
        pickImageView = (ImageView) v.findViewById(R.id.pickImageView);
        desc = (EditText) v.findViewById(R.id.desc);
        publish = (Button) v.findViewById(R.id.publish);
    }

    private void PickImage() {
        pickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.context);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            Utilities.errorToast("Camera Temporary Unavailable!");
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                            File f = new File(android.os.Environment
////                                    .getExternalStorageDirectory(), "temp.jpg");
////                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                            startActivityForResult(intent, 0);
                        } else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"),
                                    1);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode== Activity.RESULT_OK){
//            Bitmap bp = (Bitmap) data.getExtras().get("data");
//            adapter.add(Post.dummyPost);
//
//            try {
//                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
//                if(inputStream==null)Toast.makeText(getActivity(), "input stream in null", Toast.LENGTH_SHORT).show();
//                //There will saving inpustream to cloudinary
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            Toast.makeText(getActivity(), "Picture Saved", Toast.LENGTH_SHORT).show();
//        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                if (bp != null)
                    pickImageView.setImageBitmap(bp);
                Log.i("Image Picker", "Got Camera image");
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(data.getData(),
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                picturePath = cursor.getString(columnIndex);
                cursor.close();
//                File f = new File(Environment.getExternalStorageDirectory()
//                        .toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bm;
//                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
//
//                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                            btmapOptions);
//
//                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
////                    ivImage.setImageBitmap(bm);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream fOut = null;
//                    File file = new File(path, String.valueOf(System
//                            .currentTimeMillis()) + ".jpg");
//                    try {
//                        fOut = new FileOutputStream(file);
//                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
//                        fOut.flush();
//                        fOut.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else if (requestCode == 1) {
                Uri selectedImageUri = data.getData();

//                try {
//                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
//                    pickImageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
//                    if(inputStream!=null){
//                        Utilities.successToast("Got Gallery image");
//                        Log.i("Image Picker","Got Gallery image");
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                try {
                    selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImageUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    pickImageView.setImageURI(selectedImageUri);
//                Toast.makeText(getActivity(), "File Path=> " + picturePath, Toast.LENGTH_SHORT).show();

                   /* //This will be un-comment when saving to cloudinary
                    Async async = new Async();
                    async.execute(picturePath);*/

                } catch (Exception e) {

                }
            }
        }

    }


    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
