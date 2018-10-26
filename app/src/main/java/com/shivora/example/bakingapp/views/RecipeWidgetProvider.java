package com.shivora.example.bakingapp.views;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.models.RecipeIngredient;
import com.shivora.example.bakingapp.views.activities.RecipeListActivity;
import com.shivora.example.bakingapp.views.activities.RecipeStepsActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final int RC_RECIPE_LIST = 100;
    public static final int RC_STEPS_ACTIVITY = 200;
    public static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static PendingIntent mPendingIntent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_recipe_widget);

        //Get the last viewed recipe data
        Recipe recipe = getLastViewedRecipe(context);

        if (recipe==null){
            views.setViewVisibility(R.id.tv_widget_no_recipe_viewed, View.VISIBLE);
            views.setViewVisibility(R.id.widget_layout_container,View.GONE);
            Intent intent = new Intent(context,RecipeListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,RC_RECIPE_LIST,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout,pendingIntent);
        }
        else{
            String recipeName = recipe.getName();
            List<RecipeIngredient> recipeIngredients = recipe.getIngredients();
            StringBuilder ingredients = new StringBuilder();
            for (RecipeIngredient ingredient:recipeIngredients){
                ingredients.append(ingredient.getQuantity() + " " +ingredient.getMeasure()+ " " +ingredient.getIngredient() + "\n");
            }

            views.setViewVisibility(R.id.tv_widget_no_recipe_viewed,View.GONE);
            views.setViewVisibility(R.id.widget_layout_container,View.VISIBLE);
            views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
            views.setTextViewText(R.id.tv_widget_recipe_ingredients,ingredients.toString());
            views.setOnClickPendingIntent(R.id.widget_layout,mPendingIntent);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static Recipe getLastViewedRecipe(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeObject = sharedPreferences.getString(RecipeStepsActivity.KEY_RECIPE,"");
        if(recipeObject.equals("")){
            return null;
        }
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeObject,Recipe.class);

        setPendingIntent(context,recipe);
        return recipe;
    }

    private static void setPendingIntent(Context context,Recipe recipe) {
        Intent intent = new Intent(context,RecipeStepsActivity.class);
        intent.putExtra(RecipeListActivity.EXTRA_RECIPE_DETAILS,recipe);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mPendingIntent = PendingIntent.getActivity(context, RC_STEPS_ACTIVITY,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.i(TAG, "onUpdate: Widget Updated");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

