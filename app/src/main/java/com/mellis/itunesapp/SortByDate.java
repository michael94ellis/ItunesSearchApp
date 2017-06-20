package com.mellis.itunesapp;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

class SortByDate implements Comparator<Track> {

    @Override
    public int compare(Track t1, Track t2) {
        Date d1, d2;
        try {
            d1 = new SimpleDateFormat("yyyy-MM-dd").parse(t1.getReleaseDate());
            d2 = new SimpleDateFormat("yyyy-MM-dd").parse(t2.getReleaseDate());
            return d1.compareTo(d2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    private void Log(String text){
        Log.d("SortByDate", text);
    }
}