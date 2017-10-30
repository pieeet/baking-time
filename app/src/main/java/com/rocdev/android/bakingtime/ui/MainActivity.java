package com.rocdev.android.bakingtime.ui;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rocdev.android.bakingtime.database.DbUtils;
import com.rocdev.android.bakingtime.loaders.RecipesDbLoader;
import com.rocdev.android.bakingtime.loaders.RecipesHttpLoader;
import com.rocdev.android.bakingtime.widget.BakingTimeWidget;
import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.database.RecipeColumns;
import com.rocdev.android.bakingtime.database.RecipesProvider;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.utils.NetworkingUtils;

import java.util.ArrayList;
import java.util.List;


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
        if (hasConnection()) {
            return new RecipesHttpLoader(this);
        } else {
            return new RecipesDbLoader(this);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        //TODO write test
        Cursor cursor = getContentResolver()
                .query(RecipesProvider.Recipes.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.d(TAG, "No of rows retrieved " + cursor.getCount());
            //check if names are properly stored
            while (cursor.moveToNext()) {
                Log.d(TAG, cursor.getString(cursor.getColumnIndex(RecipeColumns.NAME)));
            }
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

    private boolean hasConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra(getString(R.string.key_recipe), recipe);
        startActivity(intent);
    }
}
