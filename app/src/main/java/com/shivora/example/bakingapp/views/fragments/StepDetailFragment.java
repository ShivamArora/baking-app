package com.shivora.example.bakingapp.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.models.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class StepDetailFragment extends Fragment {

    public static final String TAG = StepDetailFragment.class.getSimpleName();
    @BindView(R.id.tv_video_url)
    TextView tvVideoUrl;
    @BindView(R.id.tv_instructions)
    TextView tvInstructions;
    @BindView(R.id.fab_next)
    FloatingActionButton fabNext;
    @BindView(R.id.fab_previous)
    FloatingActionButton fabPrevious;

    RecipeStep mRecipeStep;
    int mPosition;
    StepsListFragment.OnStepChangedListener mOnStepChangedListener;
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

        tvVideoUrl.setText(mRecipeStep.getVideoURL());
        tvInstructions.setText(mRecipeStep.getDescription());
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStepChangedListener.onStepChange(mPosition+1);
            }
        });
        fabPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStepChangedListener.onStepChange(mPosition-1);
            }
        });
        return rootView;
    }
}
