package com.mellis.itunesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mellis on 6/15/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.SongHolder> {

    private static final String KEY = "track";

    private ArrayList<Track> songsList;
    private static Context context;

    public RecyclerAdapter(Context ctx, ArrayList<Track> tracks) {
        songsList = tracks;
        context = ctx;
    }

    @Override
    public RecyclerAdapter.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_row_item, parent, false);
        return new SongHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.SongHolder holder, int position) {
        Track song = songsList.get(position);
        holder.artist.setText(song.getArtistName());
        holder.title.setText(song.getTrackName());
        holder.price.setText(song.getTrackPrice());
        holder.date.setText(song.getReleaseDate());
        holder.song = song;
        holder.itemView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Track song;
        private TextView title;
        private TextView price;
        private TextView artist;
        private TextView date;

        public SongHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tvSongTitle);
            price = (TextView) v.findViewById(R.id.tvSongPrice);
            artist = (TextView) v.findViewById(R.id.tvSongArtist);
            date = (TextView) v.findViewById(R.id.tvSongDate);
        }

        @Override
        public void onClick(View v) {
            Intent trackDetailsIntent = new Intent(context, TrackDetailsActivity.class);
            trackDetailsIntent.putExtra(KEY, song);
            context.startActivity(trackDetailsIntent);
        }
    }
}