package com.barhochman.theproject.Adapters;

import android.content.Context;

import com.barhochman.theproject.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringsHelper {

    public StringsHelper(Context context) {
        StringFile.init(context);
    }

    public static String getOutcomeFile() {
        return StringFile.outcomeFile;
    }

    public static String getIncomeFile() { return StringFile.incomeFile; }

    public static String numberFormatter(Double num){
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("#,###,###,###");
        return format.format(num);
    }

    public String getFilePath() {
        return StringFile.FilePath;
    }

    public static class StringFile {
        private static String FilePath;
        private static String incomeFile;
        private static String outcomeFile;

        private static void init(Context context) {
            FilePath = context.getFilesDir().getPath() + "/";
            incomeFile = FilePath + context.getResources().getString(R.string.IncomesFileName);
            outcomeFile = FilePath + context.getResources().getString(R.string.OutcomesFileName);
        }

        public static String getOutcomeFile() {
            return outcomeFile;
        }

        public static String getIncomeFile() { return incomeFile; }

        public static String getFilePath() { return FilePath; }

    }

    public static class  UpdateUI{
        private static final String totalUpdated;

        public static String getTotalUpdated() {
            return totalUpdated;
        }

        static {
            totalUpdated = "totalUpdate";
        }
    }


}
