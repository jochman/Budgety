package com.barhochman.theproject.Adapters;

import android.content.Context;

import com.barhochman.theproject.R;

public class StringsHelper {
    public StringsHelper(Context context){
        FilePath = context.getFilesDir().getPath() + "/";
        incomeFile = FilePath  + context.getResources().getString(R.string.IncomesFileName);
        outcomeFile = FilePath + context.getResources().getString(R.string.OutcomesFileName);
    }

    public class StringFile{

    }

    public String getOutcomeFile() {
        return outcomeFile;
    }

    public String getIncomeFile() {

        return incomeFile;
    }

    public String getFilePath() {
        return FilePath;
    }

    private String FilePath;
    private String incomeFile;
    private String outcomeFile;

}
