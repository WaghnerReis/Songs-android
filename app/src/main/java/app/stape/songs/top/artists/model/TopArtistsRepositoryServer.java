package app.stape.songs.top.artists.model;

import app.stape.songs.behavior.network.APIClient;
import app.stape.songs.behavior.network.model.ArtistNetwork;
import app.stape.songs.top.artists.TopArtistsContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopArtistsRepositoryServer implements TopArtistsRepositoryContract {

    private TopArtistsContract.Presenter mPresenter;

    TopArtistsRepositoryServer(TopArtistsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getArtistsList() {
        mPresenter.makingRequisition();
        getArtistsListInServer();
    }

    @Override
    public void getArtistsListInServer() {
        Call<ArtistNetwork> call = APIClient.getInstance().getTopArtists();
        call.enqueue(new Callback<ArtistNetwork>() {
            @Override
            public void onResponse(Call<ArtistNetwork> call, Response<ArtistNetwork> response) {
                if (response.isSuccessful()) {
                    mPresenter.requisitionCompleted(response.body().getData());
                } else {
                    mPresenter.requisitionCompleted(null);
                }
            }

            @Override
            public void onFailure(Call<ArtistNetwork> call, Throwable t) {
                mPresenter.requisitionCompleted(null);
            }
        });
    }
}
