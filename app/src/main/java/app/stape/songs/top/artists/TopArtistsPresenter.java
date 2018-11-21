package app.stape.songs.top.artists;

import java.util.List;

import app.stape.songs.behavior.Application;
import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.model.Artist;
import app.stape.songs.top.artists.model.TopArtistsRepository;

public class TopArtistsPresenter implements TopArtistsContract.Presenter {

    private TopArtistsContract.Model mModel;
    private TopArtistsContract.View mView;

    public TopArtistsPresenter(TopArtistsContract.View view) {
        mModel = new TopArtistsRepository(this);
        mView = view;
    }

    @Override
    public void getArtistsList() throws NoNetworkConnectionException {
        if (Application.haveNetworkConnection()) {
            mModel.getArtistsList();
        } else {
            throw new NoNetworkConnectionException("Without internet");
        }
    }

    @Override
    public void makingRequisition() {
        mView.makingRequisition();
    }

    @Override
    public void requisitionCompleted(List<Artist> artists) {
        mView.requisitionCompleted(artists);
    }
}
