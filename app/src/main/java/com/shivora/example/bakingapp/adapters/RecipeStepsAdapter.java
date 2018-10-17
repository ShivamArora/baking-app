package com.shivora.example.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivora.example.bakingapp.R;
import com.shivora.example.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    public interface OnRecipeStepClickListener{
        void onRecipeStepClicked(int position);
    }

    List<RecipeStep> mRecipeSteps;
    OnRecipeStepClickListener mOnRecipeStepClickListener;
    public RecipeStepsAdapter(List<RecipeStep> steps,OnRecipeStepClickListener onRecipeStepClickListener){
        mRecipeSteps = steps;
        mOnRecipeStepClickListener = onRecipeStepClickListener;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_step,parent,false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int position) {
        recipeStepsViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_short_description)
        TextView tvShortDescription;
        public RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int position){
            RecipeStep step = mRecipeSteps.get(position);
            tvShortDescription.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnRecipeStepClickListener.onRecipeStepClicked(position);
        }
    }
}
