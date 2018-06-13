package com.udacity_developing_android.eiko.baking_app_project3.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExpoMediaPlayerUtils {
    private static final String LOG =
            ExpoMediaPlayerUtils.class.getSimpleName();
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;

    public static void initializeMediaSession(Context context,
                                              SimpleExoPlayer exoPlayer) {
        mMediaSession = new MediaSessionCompat(context, LOG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback(exoPlayer));
        mMediaSession.setActive(true);
    }

    public static SimpleExoPlayer initializePlayer
            (Uri mediaUri, Context context, SimpleExoPlayer mExoPlayer) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    context, trackSelector, loadControl);

            String userAgent = Util.getUserAgent(
                    context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        return mExoPlayer;
    }

    public static SimpleExoPlayer releasePlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
        return exoPlayer;
    }

    private static class MySessionCallback extends
            MediaSessionCompat.Callback {
        SimpleExoPlayer mExoPlayer;

        public MySessionCallback(SimpleExoPlayer exoPlayer) {
            mExoPlayer = exoPlayer;
        }

        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
