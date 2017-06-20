package com.mellis.itunesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static String API_URL = "https://itunes.apple.com/search?term=",
            API_RESULT_LIMIT = "&limit=";
    TextView tvLimit;
    EditText searchText;
    Switch mSwitch;
    RecyclerView mRecyclerView;
    RecyclerAdapter recyclerAdapter;
    LinearLayoutManager mLinearLayoutManager;
    ArrayList<Track> tracksList;

    SeekBar seekBar;
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            tvLimit.setText(String.valueOf(progress + 10));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    GetTracksCallback tracksCallback = new GetTracksCallback() {
        @Override
        public void displayParsedTracks(ArrayList<Track> tracks) {
            updateResultsView(tracks);
            tracksList = tracks;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLimit = (TextView) findViewById(R.id.tvLimit);
        //the string literal is "10"
        tvLimit.setText(R.string.number_10);
        searchText = (EditText) findViewById(R.id.editText);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mSwitch = (Switch) findViewById(R.id.switch1);
        mSwitch.setChecked(true);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResultsView(tracksList);
            }
        });
    }

    public void search(View v) {
        GetTracksTask getTracksTask = new GetTracksTask(this, tracksCallback);
        int limit = Integer.valueOf(tvLimit.getText().toString());
        String keyword = searchText.getText().toString();
        if (keyword.length() > 0) {
            getTracksTask.execute(API_URL + keyword + API_RESULT_LIMIT + limit);
        } else {
            Toast.makeText(this, R.string.no_keyword_given, Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View v) {
        seekBar.setProgress(0);
        tracksList.clear();
        recyclerAdapter.notifyDataSetChanged();
        searchText.setText("");
    }

    private void updateResultsView(ArrayList<Track> tracks) {
        if (!mSwitch.isChecked()) {
            Collections.sort(tracks, new SortByPrice());
        } else {
            Collections.sort(tracks, new SortByDate());
        }
        for (final Track track : tracks) {
            try {
                String rawDate = track.getReleaseDate().substring(0, 10).trim();
                Date dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(rawDate);
                String newDate = new SimpleDateFormat("MM-dd-yyyy").format(dateFormat);
                track.setReleaseDate(newDate);
                Log("Date ");
            } catch (Exception e) {
                Log("Failed to convert date");
            }
        }
        recyclerAdapter = new RecyclerAdapter(this, tracks);
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void Log(String text) {
        Log.d("MAIN", text);
    }
}
