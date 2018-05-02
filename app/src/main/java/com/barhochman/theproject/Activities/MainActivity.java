package com.barhochman.theproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.barhochman.theproject.Adapters.FIleHandler;
import com.barhochman.theproject.Adapters.StringsHelper;
import com.barhochman.theproject.Nodes.Transfers;
import com.barhochman.theproject.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        stringsHelper = new StringsHelper(getBaseContext());
        incomes = FIleHandler.read(stringsHelper.getIncomeFile());
        outcomes = FIleHandler.read(stringsHelper.getOutcomeFIle());


    }


    public static StringsHelper stringsHelper;
    public static List<Transfers> incomes;
    public static List<Transfers> outcomes;
}
