package com.rocdev.android.bakingtime.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.rocdev.android.bakingtime.database.DbUtils;
import com.rocdev.android.bakingtime.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piet on 30-10-17.
 *
 */

public class RecipesDbLoader extends AsyncTaskLoader<List<Recipe>> {


    private ArrayList<Recipe> mRecipes;

    public RecipesDbLoader(Context context) {
        super(context);
    }


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
        return DbUtils.getRecipes(getContext());
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        mRecipes = (ArrayList<Recipe>) data;
        super.deliverResult(data);
    }
}
