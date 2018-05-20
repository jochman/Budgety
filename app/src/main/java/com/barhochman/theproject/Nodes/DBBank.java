package com.barhochman.theproject.Nodes;

import android.content.Context;

import com.barhochman.theproject.Adapters.FileHandler;
import com.barhochman.theproject.Adapters.StringsHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBBank implements Serializable{
    private static ArrayList<Transfers> incomes;
    private static Double incomes_Total;
    private static ArrayList<Transfers> outcomes;
    private static Double outcomes_Total;
    private static Double total;

    public DBBank(Context context){
        // Initialize variables
        incomes = FileHandler.read(StringsHelper.getIncomeFile());
        outcomes = FileHandler.read(StringsHelper.getOutcomeFile());


        //initialize totals
        incomes_Total = sumCalc(incomes, 0.0);
        outcomes_Total = sumCalc(outcomes, 0.0);

        total = incomes_Total - outcomes_Total;
    }


    public static List<Transfers> getIncomes() {
        return incomes;
    }

    public static void setIncomes(ArrayList<Transfers> incomes) {
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

    public static void setOutcomes(ArrayList<Transfers> outcomes) {
        DBBank.outcomes = outcomes;
    }

    public static Double getOutcomes_Total() {
        return outcomes_Total;
    }

    public static void setOutcomes_Total(Double outcomes_Total) {
        DBBank.outcomes_Total = outcomes_Total;
    }

    public static Double getTotal(){return total;}

    private static Double sumCalc(List<Transfers> lst, Double sum) {
        if (!lst.isEmpty()) {
            for (Transfers t : lst) {
                sum += t.getAmount();
            }
        }
        return sum;
    }

    public static void addIncome(Transfers t){
        incomes.add(t);
        FileHandler.write(incomes, StringsHelper.StringFile.getIncomeFile());
        incomes_Total += t.getAmount();
        total += t.getAmount();
    }

    public static void addOutcome(Transfers t){
        incomes.add(t);
        incomes_Total += t.getAmount();
        total -= t.getAmount();
    }

}
