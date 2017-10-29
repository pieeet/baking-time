package com.rocdev.android.bakingtime.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;


/**
 * Created by piet on 25-10-17.
 *
 */

@Database(version = RecipesDatabase.VERSION)
public final class RecipesDatabase {

    public static final int VERSION = 1;

    @Table(RecipeColumns.class) public static final String RECIPES = "recipes";
    @Table(StepColumns.class) public static final String STEPS = "steps";
    @Table(IngredientColumns.class) public static final String INGREDIENTS = "ingredients";
}
