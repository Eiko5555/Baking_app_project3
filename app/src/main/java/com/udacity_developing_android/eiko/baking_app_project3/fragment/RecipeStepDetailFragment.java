package com.udacity_developing_android.eiko.baking_app_project3.fragment;

import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity_developing_android.eiko.baking_app_project3.R;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeDetailActivity;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeStep;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeStepDetailActivity;
import com.udacity_developing_android.eiko.baking_app_project3.utils.ExpoMediaPlayerUtils;

import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment {

    private static final String LOG =
            RecipeStepDetailFragment.class.getSimpleName();
    private static final String PLAYER_STATUE = "player_current";
    private static final String PLAYER_READY = "player_ready";
    private static final String SELECTED_POSITION = "player_selected_position";
    private static boolean expoPlayerWhenReady = true;
    SimpleExoPlayerView simpleExoPlayerView;
    TextView stepdescriptionTextview;
    Button navigationButton;
    String thumbUrl = null;
    LinearLayout stepDetailLayout;
    ImageView noImageAvailableImageview;
    String activityName = "";
    private SimpleExoPlayer exoPlayer;
    private Long videoPlayerCurrentPosition;
    private RecipeStep recipeStep = null;
    private Uri videoUri = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        int layoutFragment = R.layout.recipe_step_detail_fragment;
        final View rootview = inflater.inflate(layoutFragment, container,
                false);
        ButterKnife.bind(thumbUrl, rootview);
        if (isAdded()) {
            activityName = getActivity().getClass().getSimpleName();
        }
        if (activityName.equals(RecipeDetailActivity.class.getSimpleName())) {
            recipeStep = ((RecipeStepDetailActivity) getActivity())
                    .getCurrentStep();
        } else {
            recipeStep = ((RecipeDetailActivity) getActivity())
                    .getCurrentStep();
            navigationButton.setVisibility(View.GONE);
        }

        stepdescriptionTextview.setText(recipeStep.getShortDescription());
        videoUri = Uri.parse(recipeStep.getVideoUrl());
        thumbUrl = recipeStep.getThumbnailUrl().trim();

        if (!String.valueOf(videoUri).equals("")) {
            noImageAvailableImageview.setVisibility(View.GONE);
            simpleExoPlayerView.setVisibility(View.VISIBLE);
        } else {
            noImageAvailableImageview.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setVisibility(View.GONE);

            if (!String.valueOf(thumbUrl).equals("")) {
                Picasso.get()
                        .load(String.valueOf(thumbUrl))
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(noImageAvailableImageview);
            } else {
                Picasso.get()
                        .load(String.valueOf(R.drawable.no_image))
                        .placeholder(R.drawable.no_image)
                        .into(noImageAvailableImageview);
            }
        }
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeExpoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            initializeExpoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (exoPlayer != null) {
                expoPlayerWhenReady = exoPlayer.getPlayWhenReady();
                exoPlayer = ExpoMediaPlayerUtils.releasePlayer(exoPlayer);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            expoPlayerWhenReady = exoPlayer.getPlayWhenReady();
            videoPlayerCurrentPosition = exoPlayer.getCurrentPosition();
            exoPlayer = ExpoMediaPlayerUtils.releasePlayer(exoPlayer);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            videoPlayerCurrentPosition = savedInstanceState
                    .getLong(PLAYER_STATUE);
            expoPlayerWhenReady = savedInstanceState.getBoolean(PLAYER_READY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_STATUE, videoPlayerCurrentPosition);
        outState.putBoolean(PLAYER_READY, expoPlayerWhenReady);
    }

    public void initializeExpoPlayer() {
        if (exoPlayer == null) {
            ExpoMediaPlayerUtils.initializeMediaSession(
                    getActivity(), exoPlayer);
            if (recipeStep.getVideoUrl() != null) {
                Uri videoUri = Uri.parse(recipeStep.getVideoUrl());
                exoPlayer = ExpoMediaPlayerUtils.initializePlayer(
                        videoUri, getActivity(), exoPlayer);
            } else {
                simpleExoPlayerView.setDefaultArtwork(
                        BitmapFactory.decodeResource(
                                getResources(), R.drawable.no_video));
            }
        }
        simpleExoPlayerView.setPlayer(exoPlayer);
        if (videoPlayerCurrentPosition != null) {
            exoPlayer.seekTo(videoPlayerCurrentPosition);
        } else {
            exoPlayer.seekTo(0);
        }
        exoPlayer.setPlayWhenReady(expoPlayerWhenReady);
    }
}

