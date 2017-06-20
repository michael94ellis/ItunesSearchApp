package com.mellis.itunesapp;

/**
 * Created by Mellis on 6/13/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class GetTracksTask extends AsyncTask<String, Integer, ArrayList<Track>> {

    private static final String LOGNAME = "GetTracksTask";
    private Context context;
    private ProgressDialog progressDialog;
    private GetTracksCallback tracksCallbackInterface;


    GetTracksTask(Context ctx, GetTracksCallback tracksInterface) {
        context = ctx;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(ctx.getString(R.string.loading_tracks_dialog));
        progressDialog.setCancelable(false);
        tracksCallbackInterface = tracksInterface;
    }

    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        try {
            //Generate HTTP GET request
            URL url = new URL(params[0]);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            //Read in response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            //Return parsed track objects
            return parseJSON(sb);
        } catch (Exception e) {
            Log("Failed to get HTTP response");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
        Log("Beginning tracks URL Http Request");
    }

    @Override
    protected void onPostExecute(ArrayList<Track> results) {
        super.onPostExecute(results);
        if (results.size() > 0) {
            tracksCallbackInterface.displayParsedTracks(results);
        }
        progressDialog.dismiss();
        Log("Finished get Tracks task");
    }

    private ArrayList<Track> parseJSON(StringBuilder sb) {
        try {
            //Convert from JSON String to POJO Tracks
            JSONObject jsonObject = new JSONObject(sb.toString());
            if (jsonObject.has("results")) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                ArrayList<Track> tracksArray = new ArrayList();
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    tracksArray.add(gson.fromJson(jsonArray.get(i).toString(), Track.class));
                }
                return tracksArray;
            }
        } catch (Exception e) {
            Log("Failure to parse JSON");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    private void Log(String text) {
        Log.d(LOGNAME, text);
    }
}