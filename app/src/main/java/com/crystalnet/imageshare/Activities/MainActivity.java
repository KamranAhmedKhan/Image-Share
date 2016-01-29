package com.crystalnet.imageshare.Activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystalnet.imageshare.Fragments.EditProfileFragment;
import com.crystalnet.imageshare.Fragments.NewPostFragment;
import com.crystalnet.imageshare.Fragments.SearchFragment;
import com.crystalnet.imageshare.Fragments.SigninFragment;
import com.crystalnet.imageshare.Handlers.FirebaseHandler;
import com.crystalnet.imageshare.Model.User;
import com.crystalnet.imageshare.R;
import com.crystalnet.imageshare.ServiceListener;
import com.crystalnet.imageshare.Utils.Utilities;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    public TextView d_nameTextView;
    public ImageView d_imageView;
    public TextView d_emailTextView;
    public static Toolbar toolbar;
    private TextView d_detailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        //Toolbar and Drawer Instance
        toolbar();

        if (FirebaseHandler.getInstance().getAuthStatus()) {
            // user is logged in
            FirebaseHandler.getInstance().getLoginedUser(new ServiceListener<User, FirebaseError>() {
                @Override
                public void success(User obj) {
                    Log.e("Check: ", obj.getName());
                    Utilities.renderImage(obj.getImage(), d_imageView);
                    d_nameTextView.setText(obj.getName());
                    d_emailTextView.setText(obj.getEmail());
                    d_detailsTextView.setText(obj.getAbout());
                }

                @Override
                public void error(FirebaseError obj) {
                    Log.e("Drawer: ", obj.toString());
                }
            });
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.container, new SigninFragment()).commit();
        }else{
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.container, new SigninFragment()).commit();
        }
    }

    private void toolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        SpannableString title = new SpannableString(getResources().getString(R.string.app_name));
        title.setSpan(Typeface.createFromAsset(getAssets(), "billabong.ttf"), 0, title.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.airplay_icon);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //Drawer Views code
        d_imageView = (ImageView) headerView.findViewById(R.id.d_imageView);
        d_nameTextView = (TextView) headerView.findViewById(R.id.d_nameTextView);
        d_emailTextView = (TextView) headerView.findViewById(R.id.d_emailTextView);
        d_detailsTextView = (TextView) headerView.findViewById(R.id.d_detailsTextView);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getFragmentManager().getBackStackEntryCount()==0)
            super.onBackPressed();
        else getFragmentManager().popBackStack();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit) {
            getFragmentManager().beginTransaction().replace(R.id.container, new EditProfileFragment()).addToBackStack(null).commit();
        }else if (id == R.id.newPost) {
            getFragmentManager().beginTransaction().replace(R.id.container, new NewPostFragment()).addToBackStack(null).commit();
        }else if (id == R.id.addContact) {
            getFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).addToBackStack(null).commit();
        } else if (id == R.id.settings) {
            Utilities.successToast("Settings soon available");
        } else if (id == R.id.logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Logout");

        // set dialog message
        alertDialogBuilder
                .setMessage("You want to Logout!")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseHandler.firebaseRef.unauth();
                        Log.e("Logout: ","User is logged out");
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new SigninFragment())
                                .commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
