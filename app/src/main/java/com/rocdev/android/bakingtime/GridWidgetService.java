package com.rocdev.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rocdev.android.bakingtime.database.RecipeColumns;
import com.rocdev.android.bakingtime.database.RecipesProvider;

/**
 * Created by piet on 27-10-17.
 *
 */

public class GridWidgetService  extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = GridRemoteViewsFactory.class.getSimpleName();


    public GridRemoteViewsFactory(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        Uri recipesURI = RecipesProvider.Recipes.CONTENT_URI;
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(recipesURI, null, null, null, null);
        Log.d(TAG, "onDataSetChanged called");


    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        //Test
        if (mCursor == null) {
            Log.d(TAG, "cursor is null");
        }
        if (mCursor.getCount() == 0) {
            Log.d(TAG, "cursor is empty");
        }
        if (mCursor == null || mCursor.getCount() == 0) {
            return null;
        }
        Log.d(TAG, "cursor is not null and not empty");


        mCursor.moveToPosition(position);
        String recipeName = mCursor.getString(mCursor.getColumnIndex(RecipeColumns.NAME));
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.baking_time_widget);
        remoteViews.setTextViewText(R.id.widget_item_recipe_name, recipeName);
        Intent fillIntent = new Intent();
        // for now no need to put extras
        remoteViews.setOnClickFillInIntent(R.id.widget_item_recipe_name, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true; // ??
    }
}