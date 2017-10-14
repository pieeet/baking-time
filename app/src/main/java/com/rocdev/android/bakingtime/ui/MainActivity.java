package com.rocdev.android.bakingtime.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.utils.NetworkingUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Recipe>> {

    private List<Recipe> mRecipes;
    private static final int LOADER_ID = 1234;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipeList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            ArrayList<Recipe> recipes;

            @Override
            protected void onStartLoading() {
                if (recipes == null) {
                    forceLoad();
                } else {
                    deliverResult(recipes);
                }
            }

            @Override
            public List<Recipe> loadInBackground() {
                return NetworkingUtils.fetchRecipes();
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                recipes = (ArrayList<Recipe>) data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        //TODO write test
        Log.d(TAG, "No of recipes: " + recipes.size());
        mRecipes = recipes;
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

}
