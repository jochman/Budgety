package com.barhochman.theproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.barhochman.theproject.Adapters.FileHandler;
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
        StringsHelper stringsHelper = new StringsHelper(getBaseContext());
        incomes = FileHandler.read(stringsHelper.getIncomeFile());
        outcomes = FileHandler.read(stringsHelper.getOutcomeFile());


        //initialize totals
        incomes_Total = 0.0;
        outcomes_Total = 0.0;

        if (!incomes.isEmpty()) {
            for (Transfers t : incomes) {
                incomes_Total += t.getAmount();
            }
        }
        if (!outcomes.isEmpty()) {
            for (Transfers t : outcomes) {
                outcomes_Total += t.getAmount();
            }
        }


        // total TextView and progressbar initialize
        ((TextView) findViewById(R.id.totalSpent)).setText(String.valueOf(incomes_Total - outcomes_Total));




    }


    public static List<Transfers> incomes;
    public static Double incomes_Total;

    public static List<Transfers> outcomes;
    public static Double outcomes_Total;
}

