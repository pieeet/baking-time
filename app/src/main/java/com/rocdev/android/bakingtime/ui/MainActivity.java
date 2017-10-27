package com.rocdev.android.bakingtime.ui;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.AsyncTaskLoader;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rocdev.android.bakingtime.BakingTimeWidget;
import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.database.RecipeColumns;
import com.rocdev.android.bakingtime.database.RecipesProvider;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.utils.NetworkingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.R.attr.data;


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
                List<Recipe> recipes = NetworkingUtils.fetchRecipes();
                if (recipes != null) {
                    ContentValues[] cvs = new ContentValues[recipes.size()];
                    for (int i = 0; i < recipes.size(); i++) {
                        Recipe recipe = recipes.get(i);
                        ContentValues cv = new ContentValues();
                        cv.put(RecipeColumns.RECIPE_ID, recipe.getId());
                        cv.put(RecipeColumns.NAME, recipe.getName());
                        cv.put(RecipeColumns.IMAGE_URL, recipe.getImage());
                        cv.put(RecipeColumns.SERVINGS, recipe.getServings());
                        cvs[i] = cv;
                    }
                    ContentResolver cr = getContentResolver();
                    int rowsDeleted = cr.delete(RecipesProvider.Recipes.CONTENT_URI, null, null);
                    int rows = cr.bulkInsert(RecipesProvider.Recipes.CONTENT_URI, cvs);
                    Log.d(TAG, "no of rows deleted: " + rowsDeleted);
                    Log.d(TAG, "no of rows added: " + rows);
                }
                return recipes;
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
        Cursor cursor = getContentResolver()
                .query(RecipesProvider.Recipes.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.d(TAG, "No of rows retrieved " + cursor.getCount());
            cursor.close();

        }

        mRecipes = recipes;
        mAdapter.swapRecipes(recipes);
        //notify widgets
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidget.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        //Now update all widgets
        BakingTimeWidget.updateRecipeWidgets(this, appWidgetManager, appWidgetIds);
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
