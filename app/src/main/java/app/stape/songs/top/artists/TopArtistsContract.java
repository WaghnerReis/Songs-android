package app.stape.songs.top.artists;

import android.app.Dialog;

import java.util.List;

import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.model.Artist;

public interface TopArtistsContract {

    interface Model {

        void getArtistsList();
    }

    interface View {

        void initViews();

        void getArtistsList();

        void makingRequisition();

        void showProgressDialog();

        void requisitionCompleted(List<Artist> artists);

        void hideProgressDialog();

        void showEmptyMessage();

        Dialog getErrorDialog(int message);

        void hideEmptyMessage();

        void setupArtistsList();
    }

    interface Presenter {

        void getArtistsList() throws NoNetworkConnectionException;

        void makingRequisition();

        void requisitionCompleted(List<Artist> artists);
    }
}
