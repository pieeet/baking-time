package com.rocdev.android.bakingtime.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.rocdev.android.bakingtime.database.DbUtils;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.ui.MainActivity;
import com.rocdev.android.bakingtime.utils.NetworkingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piet on 30-10-17.
 *
 */

public class RecipesHttpLoader extends AsyncTaskLoader<List<Recipe>> {

    public RecipesHttpLoader(Context context) {
        super(context);
    }

    private ArrayList<Recipe> mRecipes;


    @Override
    protected void onStartLoading() {
        if (mRecipes == null) {
            forceLoad();
        } else {
            deliverResult(mRecipes);
        }
    }

    @Override
    public List<Recipe> loadInBackground() {
        List<Recipe> recipes = NetworkingUtils.fetchRecipes();
        if (recipes != null) {
            int rowsSaved = DbUtils.saveRecipes(getContext(), recipes);
        }
        return recipes;
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        mRecipes = (ArrayList<Recipe>) data;
        super.deliverResult(data);
    }
}
