package app.stape.songs.top.artists.artist.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import app.stape.songs.R;
import app.stape.songs.behavior.Application;
import app.stape.songs.behavior.exception.NoNetworkConnectionException;
import app.stape.songs.behavior.listener.OnItemClickListener;
import app.stape.songs.behavior.model.Artist;
import app.stape.songs.behavior.model.Track;
import app.stape.songs.top.artists.artist.ArtistContract;
import app.stape.songs.top.artists.artist.ArtistPresenter;

public class ArtistActivity extends AppCompatActivity implements ArtistContract.View, OnItemClickListener, View.OnClickListener {

    private ProgressDialog mProgress;
    private RecyclerView mTracksList;
    private ImageView mPlayAndPause;
    private TextView mEmptyText;

    private List<Track> mTracks;
    private Artist mArtist;

    private MediaPlayer mMediaPlayer;
    private int mCurrentIndex;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        mArtist = (Artist) getIntent().getSerializableExtra("ARTIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mArtist.getName());

        initViews();
        getTracksList();
    }

    @Override
    public void initViews() {
        mProgress = Application.getProgress(this, R.string.message_loading);

        mEmptyText = findViewById(R.id.empty_text);
        mTracksList = findViewById(R.id.tracks_list);

        ImageView artistPhoto = findViewById(R.id.artist_photo);
        Picasso.get().load(mArtist.getPictureMedium()).into(artistPhoto);
        artistPhoto.setTransitionName(getIntent().getStringExtra("TRANSITION"));
        artistPhoto.setOnClickListener(this);

        TextView artistName = findViewById(R.id.artist_name);
        artistName.setText(mArtist.getName());

        ImageView previous = findViewById(R.id.action_previous);
        previous.setOnClickListener(this);

        mPlayAndPause = findViewById(R.id.action_play_pause);
        mPlayAndPause.setOnClickListener(this);

        ImageView next = findViewById(R.id.action_next);
        next.setOnClickListener(this);
    }

    @Override
    public void getTracksList() {
        try {
            ArtistContract.Presenter presenter = new ArtistPresenter(this);
            presenter.getTracksList(mArtist.getId());
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
    public void requisitionCompleted(List<Track> tracks) {
        hideProgressDialog();

        if (tracks == null) {
            showEmptyMessage();

            Dialog errorDialog = getErrorDialog(R.string.error_generic);
            errorDialog.show();
        } else {
            hideEmptyMessage();

            mTracks = tracks;
            setupTracksList();
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
    public void setupTracksList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTracksList.setLayoutManager(layoutManager);
        mTracksList.setAdapter(new TrackAdapter(mTracks, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        Track track = mTracks.get(position);
        playPreview(track);

        mCurrentIndex = position;
    }

    @Override
    public void playPreview(Track track){
        prepareMedia(track.getPreview());
        mMediaPlayer.start();

        setupPlayerUi(track.getTitle());
    }

    @Override
    public void prepareMedia(String trackUrl) {
        try {
            if (mMediaPlayer != null) mMediaPlayer.stop();

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(trackUrl);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Dialog dialog = getErrorDialog(R.string.didnt_possible_load_track);
            dialog.show();
        }
    }


    @Override
    public void setupPlayerUi(String trackTitle) {
        View player = findViewById(R.id.container_player);

        TextView title = player.findViewById(R.id.track_title);
        title.setText(trackTitle);

        mPlayAndPause.setImageResource(R.drawable.ic_pause);
        player.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_previous:
                int previousIndex = mCurrentIndex - 1;

                if(previousIndex >= 0){
                    mCurrentIndex = previousIndex;

                    Track track = mTracks.get(mCurrentIndex);
                    playPreview(track);
                }
                break;
            case R.id.action_play_pause:
                if (mMediaPlayer.isPlaying()) {
                    mPlayAndPause.setImageResource(R.drawable.ic_play);
                    mMediaPlayer.pause();
                } else {
                    mPlayAndPause.setImageResource(R.drawable.ic_pause);
                    mMediaPlayer.start();
                }
                break;
            case R.id.action_next:
                int nextIndex = mCurrentIndex + 1;

                if(nextIndex <= mTracks.size()){
                    mCurrentIndex = nextIndex;

                    Track track = mTracks.get(mCurrentIndex);
                    playPreview(track);
                }
                break;
            case R.id.artist_photo:
                String uri = "http://www.deezer.com/artist/" + mArtist.getId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) mMediaPlayer.stop();
    }
}
