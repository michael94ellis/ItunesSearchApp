package com.mellis.itunesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackDetailsActivity extends AppCompatActivity {

    private static final String KEY = "track";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        try{
            if(getIntent().getExtras().get(KEY) != null){
                Track track = (Track) getIntent().getExtras().get(KEY);
                GetImageTask getImageTask = new GetImageTask((ImageView)findViewById(R.id.imageView));
                getImageTask.execute(track.getArtworkUrl100());
                ((TextView)findViewById(R.id.track)).setText(getString(R.string.track_name_detail) + track.getTrackName());
                ((TextView)findViewById(R.id.genre)).setText(getString(R.string.genre_detail) + track.getPrimaryGenreName());
                ((TextView)findViewById(R.id.artist)).setText(getString(R.string.artist_detail) + track.getArtistName());
                ((TextView)findViewById(R.id.album)).setText(getString(R.string.album_detail) + track.getCollectionName());
                ((TextView)findViewById(R.id.trackprice)).setText(getString(R.string.track_price_detail) + track.getTrackPrice());
                ((TextView)findViewById(R.id.albumprice)).setText(getString(R.string.album_price_detail) + track.getCollectionPrice());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void finish(View v){
        finish();
    }
}
