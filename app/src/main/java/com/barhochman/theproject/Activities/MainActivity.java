package com.barhochman.theproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.barhochman.theproject.Adapters.FileHandler;
import com.barhochman.theproject.Adapters.StringsHelper;
import com.barhochman.theproject.Nodes.Transfers;
import com.barhochman.theproject.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Sign In



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

    //nodes
    public static List<Transfers> incomes;
    public static Double incomes_Total;

    public static List<Transfers> outcomes;
    public static Double outcomes_Total;

    public Double sumCalc(List<Transfers> lst, Double sum){
        if (!lst.isEmpty()){
            for (Transfers t : lst){
                sum += t.getAmount();
            }
        }
        return sum;
    }

    // Google Sign In
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}

