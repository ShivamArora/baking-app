package com.shivora.example.bakingapp.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.adapters.RecipeStepsAdapter;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.models.RecipeStep;
import com.shivora.example.bakingapp.views.fragments.StepDetailFragment;
import com.shivora.example.bakingapp.views.fragments.StepsListFragment;

public class RecipeStepsActivity extends AppCompatActivity implements StepsListFragment.OnStepChangedListener {

    public static final String TAG = RecipeStepsActivity.class.getSimpleName();
    Recipe mRecipe;
    boolean mIsTwoPaned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent intent = getIntent();
        if (intent != null) {
            mRecipe = intent.getParcelableExtra(RecipeListActivity.EXTRA_RECIPE_DETAILS);
            StepsListFragment stepsListFragment = new StepsListFragment(mRecipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_fragment_container,stepsListFragment)
                    .commit();

            if (findViewById(R.id.step_detail_fragment_container)!=null){
                mIsTwoPaned = true;
                StepDetailFragment stepDetailFragment = new StepDetailFragment(mRecipe.getSteps().get(0), 0, this);
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_fragment_container, stepDetailFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onStepChange(int position) {
        if (position<=mRecipe.getSteps().size()) {
            Log.i(TAG, "onStepChange: " + position);
            if (mIsTwoPaned){
                StepDetailFragment stepDetailFragment = new StepDetailFragment(mRecipe.getSteps().get(position), position, this);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.step_detail_fragment_container, stepDetailFragment)
                        .commit();
            }
            else {
                StepDetailFragment stepDetailFragment = new StepDetailFragment(mRecipe.getSteps().get(position), position, this);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_fragment_container, stepDetailFragment)
                        .commit();
            }
        }
    }
}
