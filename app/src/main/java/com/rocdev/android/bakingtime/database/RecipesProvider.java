package com.rocdev.android.bakingtime.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import static android.R.attr.path;

/**
 * Created by piet on 25-10-17.
 *
 */

@ContentProvider(authority = RecipesProvider.AUTHORITY, database = RecipesDatabase.class)
public final class RecipesProvider {

    public static final String AUTHORITY = "com.rocdev.android.bakingtime.database.RecipesProvider";

    @TableEndpoint(table = RecipesDatabase.RECIPES) public static class Recipes {
        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipes")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");

    }

}
