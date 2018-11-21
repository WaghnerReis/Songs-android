package app.stape.songs.top.artists.artist.model;

import app.stape.songs.top.artists.artist.ArtistContract;

public class ArtistRepository implements ArtistContract.Model {

    private ArtistRepositoryContract mServer;

    public ArtistRepository(ArtistContract.Presenter presenter) {
        mServer = new ArtistRepositoryServer(presenter);
    }

    @Override
    public void getTracksList(long artistId) {
        mServer.getTracksList(artistId);
    }
}
