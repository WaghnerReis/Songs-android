package app.stape.songs.top.artists.artist.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.stape.songs.R;
import app.stape.songs.behavior.Application;
import app.stape.songs.behavior.listener.OnItemClickListener;
import app.stape.songs.behavior.model.Track;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private OnItemClickListener mItemListener;
    private List<Track> mTracks;

    TrackAdapter(List<Track> tracks, OnItemClickListener itemListener) {
        mItemListener = itemListener;
        mTracks = tracks;
    }

    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_track, viewGroup, false);
        final TrackAdapter.TrackViewHolder trackViewHolder = new TrackAdapter.TrackViewHolder(root);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(v, trackViewHolder.getAdapterPosition());
            }
        });

        return trackViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.TrackViewHolder TrackViewHolder, int i) {
        Track track = mTracks.get(i);

        TrackViewHolder.trackTitle.setText(track.getTitle());

        String formattedRank = Application.self().getString(R.string.format_rank, track.getRank());
        TrackViewHolder.rank.setText(formattedRank);
        }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {

        private TextView trackTitle;
        private TextView rank;

        TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            trackTitle = itemView.findViewById(R.id.track_title);
            rank = itemView.findViewById(R.id.rank);
        }
    }
}
