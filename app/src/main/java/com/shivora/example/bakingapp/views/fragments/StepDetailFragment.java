package com.shivora.example.bakingapp.views.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class StepDetailFragment extends Fragment {

    public static final String EXTRA_FRAGMENT_RECIPE_STEP = "extra_fragment_recipe_step";
    public static final String EXTRA_FRAGMENT_STEP_POSITION = "extra_fragment_step_position";

    public StepDetailFragment(){}

    public static final String TAG = StepDetailFragment.class.getSimpleName();
    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.tv_instructions)
    TextView tvInstructions;
    @BindView(R.id.fab_next)
    FloatingActionButton fabNext;
    @BindView(R.id.fab_previous)
    FloatingActionButton fabPrevious;

    static RecipeStep mRecipeStep;
    static int mPosition;
    static StepsListFragment.OnStepChangedListener mOnStepChangedListener;
    private SimpleExoPlayer exoPlayer;

    @SuppressLint("ValidFragment")
    public StepDetailFragment(RecipeStep recipeStep, int stepPosition, StepsListFragment.OnStepChangedListener onStepChangedListener){
        mRecipeStep = recipeStep;
        mPosition = stepPosition;
        mOnStepChangedListener = onStepChangedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details,container,false);
        ButterKnife.bind(this,rootView);
        if (savedInstanceState!=null){
            mRecipeStep = savedInstanceState.getParcelable(EXTRA_FRAGMENT_RECIPE_STEP);
            mPosition = savedInstanceState.getInt(EXTRA_FRAGMENT_STEP_POSITION);
        }

        tvInstructions.setText(mRecipeStep.getDescription());
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStepChangedListener.onStepChange(++mPosition);
            }
        });
        fabPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStepChangedListener.onStepChange(--mPosition);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EXTRA_FRAGMENT_RECIPE_STEP,mRecipeStep);
        outState.putInt(EXTRA_FRAGMENT_STEP_POSITION,mPosition);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onStart() {
        super.onStart();

        initExoPlayer();;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseExoPlayer();
    }

    private void releaseExoPlayer() {
        playerView.setPlayer(null);
        exoPlayer.stop();
        exoPlayer.release();;
        exoPlayer = null;
    }

    private void initExoPlayer() {
        if (exoPlayer==null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            playerView.setPlayer(exoPlayer);

            if (!TextUtils.isEmpty(mRecipeStep.getVideoURL())) {
                //DataSourceFactory
                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                        Util.getUserAgent(getContext(), "BakingApp"));

                Log.i(TAG, "initExoPlayer: URL: " + mRecipeStep.getVideoURL());
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mRecipeStep.getVideoURL()));

                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);
            }
            else{
                Toast.makeText(getActivity(), getString(R.string.no_video_url), Toast.LENGTH_LONG).show();
                Log.e(TAG, "initExoPlayer: No Video URL Found!");
            }
        }


    }
}
