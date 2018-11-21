package app.stape.songs.behavior.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.stape.songs.behavior.model.Artist;

public class ArtistNetwork {

    @SerializedName("data")
    private List<Artist> data;

    public List<Artist> getData() {
        return data;
    }

    public void setData(List<Artist> data) {
        this.data = data;
    }
}
