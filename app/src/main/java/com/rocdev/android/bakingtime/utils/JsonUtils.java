package com.rocdev.android.bakingtime.utils;

import com.rocdev.android.bakingtime.models.Ingredient;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piet on 14-10-17.
 *
 */

class JsonUtils {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SERVINGS = "servings";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_MEASURE = "measure";
    private static final String KEY_INGREDIENT = "ingredient";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_SHORT_DESCRIPTION = "shortDescription";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VIDEO_URL = "videoURL";
    private static final String KEY_THUMB_URL = "thumbnailURL";


    static ArrayList<Recipe> extractRecipesFromJson(String jsonString) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(jsonString);
            for (int i = 0; i < root.length(); i++) {
                JSONObject recipeJO = root.getJSONObject(i);
                int id = recipeJO.getInt(KEY_ID);
                String recipeName = recipeJO.getString(KEY_NAME);
                int servings = recipeJO.getInt(KEY_SERVINGS);
                String image = recipeJO.getString(KEY_IMAGE);
                JSONArray ingredientsJA = recipeJO.getJSONArray(KEY_INGREDIENTS);
                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; j < ingredientsJA.length(); j++) {
                    JSONObject ingredientJO = ingredientsJA.getJSONObject(j);
                    double quantity = ingredientJO.getDouble(KEY_QUANTITY);
                    String measure = ingredientJO.getString(KEY_MEASURE);
                    String ingredientName = ingredientJO.getString(KEY_INGREDIENT);
                    ingredients.add(new Ingredient(ingredientName, measure, quantity));
                }
                List<Step> steps = new ArrayList<>();
                JSONArray stepsJA = recipeJO.getJSONArray(KEY_STEPS);
                for (int j = 0; j < stepsJA.length(); j++) {
                    JSONObject stepJO = stepsJA.getJSONObject(j);
                    int stepId = stepJO.getInt(KEY_ID);
                    String shortDescription = stepJO.getString(KEY_SHORT_DESCRIPTION);
                    String description = stepJO.getString(KEY_DESCRIPTION);
                    String videoUrl = stepJO.getString(KEY_VIDEO_URL);
                    String thumbnailURL = stepJO.getString(KEY_THUMB_URL);
                    steps.add(new Step(stepId, shortDescription, description, videoUrl,
                            thumbnailURL));
                }
                recipes.add(new Recipe(id, recipeName, servings, image, ingredients, steps));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
