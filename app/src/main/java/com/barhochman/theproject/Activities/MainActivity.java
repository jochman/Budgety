package com.barhochman.theproject.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.barhochman.theproject.Adapters.DriveAdapter;
import com.barhochman.theproject.Adapters.FileHandler;
import com.barhochman.theproject.Adapters.StringsHelper;
import com.barhochman.theproject.Nodes.Transfers;
import com.barhochman.theproject.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //nodes
    public static List<Transfers> incomes;
    public static Double incomes_Total;
    public static List<Transfers> outcomes;
    public static Double outcomes_Total;

    //google API
    DriveAdapter driveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Sign In
        DriveSignIn();
        //initialize income/outcomes
        listsInit();


    }

    private void DriveSignIn() {
         driveAdapter = new DriveAdapter(this);
    }

    private void listsInit() {
        // Initialize variables
        StringsHelper stringsHelper = new StringsHelper(getBaseContext());
        incomes = FileHandler.read(stringsHelper.getIncomeFile());
        outcomes = FileHandler.read(stringsHelper.getOutcomeFile());


        //initialize totals
        incomes_Total = sumCalc(incomes, 0.0);
        outcomes_Total = sumCalc(outcomes, 0.0);


        // total TextView and progressbar initialize
        ((TextView) findViewById(R.id.totalSpent)).setText(String.valueOf(incomes_Total - outcomes_Total));
    }

    public Double sumCalc(List<Transfers> lst, Double sum) {
        if (!lst.isEmpty()) {
            for (Transfers t : lst) {
                sum += t.getAmount();
            }
        }
        return sum;
    }



}

