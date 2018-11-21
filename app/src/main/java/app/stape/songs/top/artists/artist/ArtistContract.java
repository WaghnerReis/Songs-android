package app.stape.songs.top.artists.artist;

import android.app.Dialog;

import java.util.List;

import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.model.Track;

public interface ArtistContract {

    interface Model {

        void getTracksList(long artistId);
    }

    interface View {

        void initViews();

        void getTracksList();

        void makingRequisition();

        void showProgressDialog();

        void requisitionCompleted(List<Track> tracks);

        void hideProgressDialog();

        void showEmptyMessage();

        Dialog getErrorDialog(int message);

        void hideEmptyMessage();

        void setupTracksList();

         void playPreview(Track track);

        void prepareMedia(String trackUrl);

        void setupPlayerUi(String trackTitle);
    }

    interface Presenter {

        void getTracksList(long artistId) throws NoNetworkConnectionException;

        void makingRequisition();

        void requisitionCompleted(List<Track> tracks);
    }
}
