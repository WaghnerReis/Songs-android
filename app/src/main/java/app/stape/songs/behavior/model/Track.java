package app.stape.songs.behavior.model;

import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("title_short")
    private String title;
    @SerializedName("duration")
    private long duration;
    @SerializedName("preview")
    private String preview;
    @SerializedName("rank")
    private long rank;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }
}
