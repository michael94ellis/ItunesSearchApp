package com.mellis.itunesapp;

import java.util.Comparator;

class SortByPrice implements Comparator<Track> {

    @Override
    public int compare(Track t1, Track t2) {
        double a = Double.valueOf(t1.getTrackPrice());
        double b = Double.valueOf(t2.getTrackPrice());
        if (a>b){
            return 1;
        }else if(a==b){
            return 0;
        }else{
            return -1;
        }
    }
}