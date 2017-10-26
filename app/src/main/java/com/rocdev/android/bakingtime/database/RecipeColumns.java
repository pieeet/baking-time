package com.rocdev.android.bakingtime.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by piet on 25-10-17.
 */

public interface RecipeColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull
    String RECIPE_ID = "recipe_id";
    @DataType(TEXT) @NotNull String NAME = "name";
    @DataType(INTEGER) @NotNull String SERVINGS = "servings";
    @DataType(TEXT) String IMAGE_URL = "image_url";

}
