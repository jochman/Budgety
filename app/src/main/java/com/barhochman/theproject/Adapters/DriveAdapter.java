package com.barhochman.theproject.Adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

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

public class DriveAdapter {
    private GoogleCredential aCredential;
    private Context context;

    public DriveAdapter(Context context){
        this.context = context;
        try {
            aCredential = GoogleCredential
                    .fromStream(context.getResources().openRawResource(R.raw.client_secret))
                    .createScoped(DriveScopes.all());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getResultsFromApi();
    }
    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (!isDeviceOnline()) {
            Toast.makeText(context, "No network connection available.", Toast.LENGTH_LONG).show();
        } else {
            new MakeRequestTask(aCredential).execute();
        }
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * An asynchronous task that handles the Drive API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.drive.Drive mService;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("appName")
                    .build();
        }

        /**
         * Background task to call Drive API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of up to 10 file names and IDs.
         *
         * @return List of Strings describing files, or an empty list if no files
         * found.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> fileInfo = new ArrayList<>();
            FileList result = mService.files().list()
                    .setCorpus("user")
                    .setQ("'public_gdrive_folder_id' in parents")
                    .setOrderBy("name")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files != null) {
                for (File file : files) {
                    fileInfo.add(String.format("%s (%s)\n",
                            file.getName(), file.getId()));
                }
            }
            return fileInfo;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(List<String> output) {
            if (output == null || output.size() == 0) {
                Toast.makeText(context, "No results returned.", Toast.LENGTH_LONG).show();
            } else {
                //output.add(0, "Data retrieved using the Drive API:");
                Toast.makeText(context, TextUtils.join("\n", output), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                Toast.makeText(context, "The following error occurred:\n"
                        + mLastError.getMessage(), Toast.LENGTH_LONG).show();

                // }
            } else {
                Toast.makeText(context, "Request cancelled.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
