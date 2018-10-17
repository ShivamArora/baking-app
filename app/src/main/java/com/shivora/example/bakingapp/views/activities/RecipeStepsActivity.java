package com.shivora.example.bakingapp.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.views.fragments.StepsListFragment;

public class RecipeStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent intent = getIntent();
        if (intent != null) {
            Recipe recipe = intent.getParcelableExtra(RecipeListActivity.EXTRA_RECIPE_DETAILS);
            StepsListFragment stepsListFragment = new StepsListFragment(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_fragment_container,stepsListFragment)
                    .commit();
        }
    }
}
