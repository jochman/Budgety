package com.barhochman.theproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barhochman.theproject.Nodes.DBBank;
import com.barhochman.theproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    final int RC_SIGN_IN = 100;
    //TAG
    String TAG = "Bar ";
    //nodes
    DBBank dbBank;
    //google API
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //google sign in
        mGoogleSignInClient = buildGoogleSignInClient();
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        } else {
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
        Task<GoogleSignInAccount> Task = GoogleSignIn.getSignedInAccountFromIntent(mGoogleSignInClient.getSignInIntent());

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            final Uri personPhoto = acct.getPhotoUrl();


            ((TextView) findViewById(R.id.nameinApp)).setText(acct.getDisplayName());
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);

            Picasso.get().load(personPhoto).into(imageView);

        }


        //initialize income/outcomes
        dbBank = new DBBank(this);

        //initialize first fragment
        //mainList frg = new mainList();
        //getFragmentManager().beginTransaction().replace(R.id.container, frg).commit();


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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button: {
                signIn();
                view.setVisibility(View.GONE);
                findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            }
            break;
            case R.id.sign_out_button: {
                mGoogleSignInClient.signOut();
                view.setVisibility(View.GONE);
                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            }
            break;

        }
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    private void signIn() {
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }
}

