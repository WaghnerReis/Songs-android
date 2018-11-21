package app.stape.songs.top.artists.artist.model;

import app.stape.songs.behavior.network.APIClient;
import app.stape.songs.behavior.network.model.TrackNetwork;
import app.stape.songs.top.artists.artist.ArtistContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistRepositoryServer implements ArtistRepositoryContract {

    private ArtistContract.Presenter mPresenter;

    ArtistRepositoryServer(ArtistContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getTracksList(long artistId) {
        mPresenter.makingRequisition();
        getTracksListInServer(artistId);
    }

    @Override
    public void getTracksListInServer(long artistId) {
        Call<TrackNetwork> call = APIClient.getInstance().getTracks(artistId);
        call.enqueue(new Callback<TrackNetwork>() {
            @Override
            public void onResponse(Call<TrackNetwork> call, Response<TrackNetwork> response) {
                if (response.isSuccessful()) {
                    mPresenter.requisitionCompleted(response.body().getData());
                } else {
                    mPresenter.requisitionCompleted(null);
                }
            }

            @Override
            public void onFailure(Call<TrackNetwork> call, Throwable t) {
                mPresenter.requisitionCompleted(null);
            }
        });
    }
}