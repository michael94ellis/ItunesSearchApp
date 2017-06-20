package com.mellis.itunesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mellis on 6/2/2017.
 */

public class GetImageTask extends AsyncTask<String, String, Bitmap> {

    private static final String LOGNAME = "GetImageTask";
    ImageView iv;

    GetImageTask(ImageView imageView){
        iv = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log("Beginning image task");
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            iv.setImageBitmap(result);
            Log("Picture updated");
        }
    }
    private void Log(String text) {
        Log.d(LOGNAME, text);
    }
}
