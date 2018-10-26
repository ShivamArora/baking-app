package com.shivora.example.bakingapp.views.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.adapters.RecipeStepsAdapter;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.models.RecipeStep;
import com.shivora.example.bakingapp.views.RecipeWidgetProvider;
import com.shivora.example.bakingapp.views.fragments.StepDetailFragment;
import com.shivora.example.bakingapp.views.fragments.StepsListFragment;

import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeStepsActivity extends AppCompatActivity implements StepsListFragment.OnStepChangedListener {

    public static final String TAG = RecipeStepsActivity.class.getSimpleName();
    public static final String KEY_RECIPE_POSITION = "key_recipe_position";
    public static final String KEY_RECIPE_NAME = "key_recipe_name";
    public static final String KEY_RECIPE_INGREDIENTS = "key_recipe_ingredients";
    public static final String KEY_RECIPE = "key_recipe";
    Recipe mRecipe;
    boolean mIsTwoPaned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent intent = getIntent();
        if (intent != null) {
            mRecipe = intent.getParcelableExtra(RecipeListActivity.EXTRA_RECIPE_DETAILS);
            saveLastViewedRecipeDetails();
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

    private void saveLastViewedRecipeDetails() {
        Gson gson = new Gson();
        String recipeObject = gson.toJson(mRecipe);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RECIPE,recipeObject);
        editor.apply();
        Log.i(TAG, "saveLastViewedRecipeDetails: "+mRecipe.getName());
        updateWidgetDetails();
    }

    private void updateWidgetDetails() {
        Intent updateWidget = new Intent(this,RecipeWidgetProvider.class);
        updateWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //Get the list of widget ids
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(),RecipeWidgetProvider.class));
        updateWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(updateWidget);
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
