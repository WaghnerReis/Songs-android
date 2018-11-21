package app.stape.songs.top.artists.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.stape.songs.R;
import app.stape.songs.behavior.listener.OnItemClickListener;
import app.stape.songs.behavior.model.Artist;

public class ArtistsAdapter  extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

    private OnItemClickListener mItemListener;
    private List<Artist> mArtists;

    ArtistsAdapter(List<Artist> artists, OnItemClickListener itemListener) {
        mItemListener = itemListener;
        mArtists = artists;
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_artists, viewGroup, false);
        final ArtistsViewHolder artistsViewHolder = new ArtistsViewHolder(root);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(v, artistsViewHolder.getAdapterPosition());
            }
        });

        return artistsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder artistsViewHolder, int i) {
       Artist artist = mArtists.get(i);
        Picasso.get()
                .load(artist.getPictureSmall())
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.ic_menu_camera)
                .into(artistsViewHolder.artistPhoto);

        artistsViewHolder.artistName.setText(artist.getName());
        artistsViewHolder.artistPosition.setText(String.valueOf(artist.getPosition()));
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    static class ArtistsViewHolder extends RecyclerView.ViewHolder {

        private ImageView artistPhoto;
        private TextView artistName;
        private TextView artistPosition;

        ArtistsViewHolder(@NonNull View itemView) {
            super(itemView);
            artistPhoto = itemView.findViewById(R.id.artist_photo);
            artistName = itemView.findViewById(R.id.artist_name);
            artistPosition = itemView.findViewById(R.id.artist_position);
        }
    }
}
