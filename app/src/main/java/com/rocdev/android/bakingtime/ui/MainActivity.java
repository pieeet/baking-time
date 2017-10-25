package com.rocdev.android.bakingtime.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.utils.NetworkingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Recipe>>, RecipesAdapter.OnRecipeClickedCallback {

    private static final int LOADER_ID = 1234;
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> mRecipes;
    private RecipesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipeList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getBoolean(R.bool.tablet_land)) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recyclerView.setLayoutManager(layoutManager);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        mAdapter = new RecipesAdapter(this, this, mRecipes);
        recyclerView.setAdapter(mAdapter);

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
        mRecipes = recipes;
        mAdapter.swapRecipes(recipes);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra(getString(R.string.key_recipe), recipe);
        startActivity(intent);
    }
}
