package app.stape.songs.behavior.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.stape.songs.behavior.model.Track;

public class TrackNetwork {

    @SerializedName("data")
    private List<Track> data;

    public List<Track> getData() {
        return data;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }
}
