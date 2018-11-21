package app.stape.songs.top.artists.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.stape.songs.R;
import app.stape.songs.behavior.Application;
import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.listener.OnItemClickListener;
import app.stape.songs.behavior.model.Artist;
import app.stape.songs.top.artists.TopArtistsContract;
import app.stape.songs.top.artists.TopArtistsPresenter;
import app.stape.songs.top.artists.artist.view.ArtistActivity;

public class TopArtistsActivity extends AppCompatActivity implements TopArtistsContract.View, OnItemClickListener {

    private ProgressDialog mProgress;
    private RecyclerView mArtistsList;
    private TextView mEmptyText;
    private List<Artist> mArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_artists);

        initViews();
        getArtistsList();
    }


    @Override
    public void initViews() {
        mProgress = Application.getProgress(this, R.string.message_loading);

        mEmptyText = findViewById(R.id.empty_text);
        mArtistsList = findViewById(R.id.artists_list);
    }

    @Override
    public void getArtistsList() {
        try {
            TopArtistsContract.Presenter presenter = new TopArtistsPresenter(this);
            presenter.getArtistsList();
        } catch (NoNetworkConnectionException e) {
            Dialog errorDialog = getErrorDialog(R.string.error_without_network);
            errorDialog.show();
        }
    }

    @Override
    public Dialog getErrorDialog(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void makingRequisition() {
        showProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        if (!mProgress.isShowing()) mProgress.show();
    }

    @Override
    public void requisitionCompleted(List<Artist> artists) {
        hideProgressDialog();

        if (artists == null) {
            showEmptyMessage();

            Dialog errorDialog = getErrorDialog(R.string.error_generic);
            errorDialog.show();
        } else {
            hideEmptyMessage();

            mArtists = artists;
            setupArtistsList();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgress.isShowing()) mProgress.dismiss();
    }

    @Override
    public void showEmptyMessage() {
        mEmptyText.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideEmptyMessage() {
        mEmptyText.setVisibility(View.GONE);
    }

    @Override
    public void setupArtistsList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mArtistsList.setLayoutManager(layoutManager);
        mArtistsList.setAdapter(new ArtistsAdapter(mArtists, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        ImageView artistPhoto = view.findViewById(R.id.artist_photo);

        Intent intent = new Intent(this, ArtistActivity.class);
        intent.putExtra("TRANSITION", artistPhoto.getTransitionName());
        intent.putExtra("ARTIST", mArtists.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                artistPhoto, artistPhoto.getTransitionName());
        startActivity(intent, options.toBundle());
    }
}
