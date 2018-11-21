package app.stape.songs.top.artists.artist;

import java.util.List;

import app.stape.songs.behavior.Application;
import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.model.Track;
import app.stape.songs.top.artists.artist.model.ArtistRepository;

public class ArtistPresenter implements ArtistContract.Presenter {

    private ArtistContract.Model mModel;
    private ArtistContract.View mView;

    public ArtistPresenter(ArtistContract.View view) {
        mModel = new ArtistRepository(this);
        mView = view;
    }

    @Override
    public void getTracksList(long artistId) throws NoNetworkConnectionException {
        if (Application.haveNetworkConnection()) {
            mModel.getTracksList(artistId);
        } else {
            throw new NoNetworkConnectionException("Without internet");
        }
    }

    @Override
    public void makingRequisition() {
        mView.makingRequisition();
    }

    @Override
    public void requisitionCompleted(List<Track> tracks) {
        mView.requisitionCompleted(tracks);
    }
}
