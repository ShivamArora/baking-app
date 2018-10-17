package com.shivora.example.bakingapp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.adapters.RecipesListAdapter;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.networking.NetworkingUtils;
import com.shivora.example.bakingapp.networking.RecipeApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeListActivity extends AppCompatActivity implements RecipesListAdapter.OnRecipeClickListener {

    public static final String TAG = RecipeListActivity.class.getSimpleName();
    @BindView(R.id.rv_recipes_list)
    RecyclerView rvRecipesList;

    List<Recipe> recipeList;
    RecipesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        setupViews();
        getRecipesList();
    }

    private void setupViews() {
        adapter = new RecipesListAdapter(new ArrayList<Recipe>(),this);
        if (isTablet()){
            rvRecipesList.setLayoutManager(new GridLayoutManager(this,3));
        }
        else {
            rvRecipesList.setLayoutManager(new LinearLayoutManager(this));
        }
        rvRecipesList.setAdapter(adapter);
    }

    private boolean isTablet(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float screenWidth = metrics.widthPixels/metrics.density;
        return screenWidth>=600;
    }

    private void getRecipesList() {
        Retrofit retrofit = NetworkingUtils.getRetrofitInstance();
        RecipeApi recipeApi = retrofit.create(RecipeApi.class);
        Call<List<Recipe>> call = recipeApi.getRecipesList();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG, "onResponse: Success");
                recipeList = response.body();
                setRecipesList(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

        Log.d(TAG, "Fetching Recipes List");
        /*for (Recipe recipe: recipeList){
            Log.d(TAG, "Recipe: "+recipe.getName());
        }*/
    }

    private void setRecipesList(List<Recipe> recipeList) {
        adapter.setRecipesList(recipeList);
    }

    @Override
    public void onRecipeClicked(int position) {
        Log.i(TAG, "onRecipeClicked: "+position);
    }
}
