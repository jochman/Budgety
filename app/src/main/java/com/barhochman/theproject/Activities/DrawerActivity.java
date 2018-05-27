package com.barhochman.theproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.barhochman.theproject.Adapters.FileHandler;
import com.barhochman.theproject.Adapters.IOHandler;
import com.barhochman.theproject.Adapters.StringsHelper;
import com.barhochman.theproject.Fragments.AddTransfer;
import com.barhochman.theproject.Fragments.MainList;
import com.barhochman.theproject.Nodes.DBBank;
import com.barhochman.theproject.Nodes.Transfers;
import com.barhochman.theproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainList.OnMainListFragmentInteractionListener, AddTransfer.OnFragmentInteractionListener {

    final int RC_SIGN_IN = 100;

    public FragmentManager getCustomFragmentManager(){
        return getSupportFragmentManager();
    }

    //TAG
    String TAG = "Bar ";


    //google API
    GoogleSignInClient mGoogleSignInClient;
    static GoogleSignInAccount acct;
    DriveResourceClient mDriveResourceClient;
    DriveClient mDriveClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IOHandler.FragmentSwapper(getCustomFragmentManager(), R.layout.fragment_add_transfer);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //google sign in
        mGoogleSignInClient = buildGoogleSignInClient();

        updateViewWithGoogleSignInAccountTask(GoogleSignIn.getSignedInAccountFromIntent(mGoogleSignInClient.getSignInIntent()));

        acct = GoogleSignIn.getLastSignedInAccount(this);



        //initialize income/outcomes
        new StringsHelper(this);


        /*
        code for qa


        ArrayList<Transfers> tempInc = new ArrayList<>();
        ArrayList<Transfers> tempOut = new ArrayList<>();
        tempInc.add(new Transfers("bar", 500.0, "pleasure"));
        tempInc.add(new Transfers("bar", 500.0, "pleasure"));
        tempInc.add(new Transfers("bar", 500.0, "pleasure"));
        tempOut.add(new Transfers("bar", 500.0, "pleasure"));
        tempOut.add(new Transfers("bar", 500.0, "pleasure"));
        tempOut.add(new Transfers("bar", 500.0, "pleasure"));



        tempOut.add(new Transfers("bar", 500.0, "pleasure"));
        tempOut.add(new Transfers("bar", 500.0, "pleasure"));
        tempOut.add(new Transfers("bar", 500.0, "pleasure"));


        end of qa


        FileHandler.write(tempInc, StringsHelper.getIncomeFile());
        FileHandler.write(tempOut, StringsHelper.getOutcomeFile());
        */
        new DBBank(this);

        //initialize main fragment
        Fragment fragment = new MainList();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, StringsHelper.fragmentTag.getMainList()).commit();



    }

    public void invalidate(){
        try {
            Fragment total = Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(StringsHelper.fragmentTag.getMainList()));
            //totals update
            ((TextView) Objects.requireNonNull(total.getView()).findViewById(R.id.totalSpent)).setText(StringsHelper.numberFormatter(DBBank.getTotal()));
            ConstraintLayout back = Objects.requireNonNull(total.getView()).findViewById(R.id.top_total);
            if (DBBank.getTotal() < 0) {
                back.findViewById(R.id.top_total).setBackgroundColor(Color.parseColor("#c40824"));
            } else {
                back.findViewById(R.id.top_total).setBackgroundColor(Color.parseColor("#86b24f"));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void floatingBarShow(Boolean b){
        FloatingActionButton fab = findViewById(R.id.fab);
        try {
            if (!b) {
                fab.hide();
            } else {
                fab.show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            floatingBarShow(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem  item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            acct = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }


    private void signIn() {
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...TODO
                    }
                });
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


    private void updateViewWithGoogleSignInAccountTask(Task<GoogleSignInAccount> task) {
        Log.i(TAG, "Update view with sign in account task");
        task.addOnSuccessListener(
                new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.i(TAG, "Sign in success");
                        // Build a drive client.
                        mDriveClient = Drive.getDriveClient(getApplicationContext(), googleSignInAccount);
                        // Build a drive resource client.
                        mDriveResourceClient =
                                Drive.getDriveResourceClient(getApplicationContext(), googleSignInAccount);

                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Sign in failed", e);
                            }
                        });
    }

    protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }

    @Override
    public void onMainListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
