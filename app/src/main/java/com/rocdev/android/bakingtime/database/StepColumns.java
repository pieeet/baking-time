package com.rocdev.android.bakingtime.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by piet on 28-10-17.
 *
 */

public interface StepColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement
    String _ID = "_id";

    @DataType(INTEGER) @NotNull
    String STEP_ID = "step_id";

    @DataType(INTEGER) @NotNull
    String RECIPE_ID = "recipe_id";

    @DataType(TEXT) @NotNull
    String SHORT_DESCRIPTION = "short_description";

    @DataType(TEXT) @NotNull
    String DESCRIPTION = "description";

    @DataType(TEXT)
    String VIDEO_URL = "video_url";

    @DataType(TEXT)
    String THUMBNAIL_URL = "thumbnail_url";

}
