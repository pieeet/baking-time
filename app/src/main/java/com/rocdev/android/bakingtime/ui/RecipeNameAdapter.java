package com.rocdev.android.bakingtime.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;

import java.util.List;

/**
 * Created by piet on 15-10-17.
 *
 */

class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.ViewHolder> {


    private List<Recipe> mRecipes;
    private Context mContext;
    private OnRecipeClickedCallback mCallback;

    RecipeNameAdapter(Context context, OnRecipeClickedCallback callback,
                             List<Recipe> recipes) {
        this.mContext = context;
        this.mCallback = callback;
        this.mRecipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View recipeView = inflater.inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onRecipeClicked(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final View cardView;
        final TextView tvRecipeName;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_recipe_card);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        }
    }

    interface OnRecipeClickedCallback {
        void onRecipeClicked(Recipe recipe);
    }

    void swapRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        super.notifyDataSetChanged();
    }
}
