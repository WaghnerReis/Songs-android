package app.stape.songs.top.artists.artist.model;

public interface ArtistRepositoryContract {

    void getTracksList(long artistId);

    void getTracksListInServer(long artistId);
}
