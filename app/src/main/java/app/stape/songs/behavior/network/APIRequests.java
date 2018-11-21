package app.stape.songs.behavior.network;

import app.stape.songs.behavior.network.model.ArtistNetwork;
import app.stape.songs.behavior.network.model.TrackNetwork;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRequests {


    @GET("/chart/0/artists")
    Call<ArtistNetwork> getTopArtists();

    @GET("/artist/{artistId}/top?limit=20")
    Call<TrackNetwork> getTracks(@Path("artistId") long artistId);
}
