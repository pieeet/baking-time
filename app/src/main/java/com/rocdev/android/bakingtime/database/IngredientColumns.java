package com.rocdev.android.bakingtime.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by piet on 28-10-17.
 *
 */

public interface IngredientColumns {
    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(INTEGER) @NotNull
    String RECIPE_ID = "recipe_id";

    @DataType(TEXT) @NotNull
    String INGREDIENT_NAME = "name";

    @DataType(REAL) @NotNull
    String QUANTITY = "quantity";

    @DataType(TEXT) @NotNull
    String MEASURE = "measure";
}
