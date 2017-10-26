package com.rocdev.android.bakingtime.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;


/**
 * Created by piet on 25-10-17.
 *
 */

@Database(version = RecipesDatabase.VERSION)
public class RecipesDatabase {

    public static final int VERSION = 1;

    @Table(RecipeColumns.class) public static final String RECIPES = "recipes";
}
