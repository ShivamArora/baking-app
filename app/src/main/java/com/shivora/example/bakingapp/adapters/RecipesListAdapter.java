package com.shivora.example.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder> {

    public interface OnRecipeClickListener{
        void onRecipeClicked(int position);
    }
    private List<Recipe> mRecipesList;
    private OnRecipeClickListener mRecipeClickListener;
    public RecipesListAdapter(List<Recipe> mRecipesList,OnRecipeClickListener recipeClickListener) {
        this.mRecipesList = mRecipesList;
        this.mRecipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.item_recipe,parent,false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int position) {
        recipesViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

    public void setRecipesList(List<Recipe> recipesList){
        mRecipesList = recipesList;
        notifyDataSetChanged();
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView tvRecipeName;
        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int position){
            Recipe recipe = mRecipesList.get(position);
            this.itemView.setId(recipe.getId());
            tvRecipeName.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mRecipeClickListener.onRecipeClicked(position);
        }
    }
}
