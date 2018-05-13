package com.barhochman.theproject.Nodes;

import android.content.Context;

import com.barhochman.theproject.Adapters.FileHandler;
import com.barhochman.theproject.Adapters.StringsHelper;

import java.util.List;

public class DBBank {
    private static List<Transfers> incomes;
    private static Double incomes_Total;
    private static List<Transfers> outcomes;

    public static List<Transfers> getIncomes() {
        return incomes;
    }

    public static void setIncomes(List<Transfers> incomes) {
        DBBank.incomes = incomes;
    }

    public static Double getIncomes_Total() {
        return incomes_Total;
    }

    public static void setIncomes_Total(Double incomes_Total) {
        DBBank.incomes_Total = incomes_Total;
    }

    public static List<Transfers> getOutcomes() {
        return outcomes;
    }

    public static void setOutcomes(List<Transfers> outcomes) {
        DBBank.outcomes = outcomes;
    }

    public static Double getOutcomes_Total() {
        return outcomes_Total;
    }

    public static void setOutcomes_Total(Double outcomes_Total) {
        DBBank.outcomes_Total = outcomes_Total;
    }

    private static Double outcomes_Total;

    public DBBank(Context context){
        // Initialize variables
        StringsHelper stringsHelper = new StringsHelper(context);
        incomes = FileHandler.read(stringsHelper.getIncomeFile());
        outcomes = FileHandler.read(stringsHelper.getOutcomeFile());


        //initialize totals
        incomes_Total = sumCalc(incomes, 0.0);
        outcomes_Total = sumCalc(outcomes, 0.0);
    }

    private static Double sumCalc(List<Transfers> lst, Double sum) {
        if (!lst.isEmpty()) {
            for (Transfers t : lst) {
                sum += t.getAmount();
            }
        }
        return sum;
    }
}
