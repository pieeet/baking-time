package com.rocdev.android.bakingtime.widget;

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
 * Created by piet on 28-10-17.
 *
 */

public class ConfigRecipesListAdapter extends RecyclerView.Adapter<ConfigRecipesListAdapter.ViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipes;

    ConfigRecipesListAdapter(Context context, List<Recipe> recipes) {
        mRecipes = recipes;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_widget_config_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.recipeName.setText(recipe.getName());
    }


    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_widget_config_listitem_recipe_name);
        }
    }
}
