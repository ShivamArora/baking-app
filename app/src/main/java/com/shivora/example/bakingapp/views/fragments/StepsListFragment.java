package com.shivora.example.bakingapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.adapters.RecipeStepsAdapter;
import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.models.RecipeIngredient;
import com.shivora.example.bakingapp.models.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListFragment extends Fragment implements RecipeStepsAdapter.OnRecipeStepClickListener {

    public static final String TAG = StepsListFragment.class.getSimpleName();
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    RecipeStepsAdapter adapter;
    Recipe mRecipe;

    public StepsListFragment(Recipe recipe){
        mRecipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list,container,false);
        ButterKnife.bind(this,rootView);

        for (RecipeIngredient ingredient: mRecipe.getIngredients()){
            tvIngredients.append(ingredient.getQuantity()+" "+ingredient.getMeasure()+" "+ingredient.getIngredient()+"\n");
        }

        adapter =  new RecipeStepsAdapter(mRecipe.getSteps(),this);
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSteps.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onRecipeStepClicked(int position) {
        Log.i(TAG, "onRecipeStepClicked: "+position);
    }
}
