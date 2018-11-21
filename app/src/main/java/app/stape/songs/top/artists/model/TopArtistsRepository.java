package app.stape.songs.top.artists.model;

import app.stape.songs.top.artists.TopArtistsContract;

public class TopArtistsRepository implements TopArtistsContract.Model {

    private TopArtistsRepositoryContract mServer;

    public TopArtistsRepository(TopArtistsContract.Presenter presenter) {
        mServer = new TopArtistsRepositoryServer(presenter);
    }


    @Override
    public void getArtistsList() {
        mServer.getArtistsList();
    }
}
