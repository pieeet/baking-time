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

    static ArrayList<Recipe> extractRecipesFromJson(String jsonString) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(jsonString);
            for (int i = 0; i < root.length(); i++) {
                JSONObject recipeJs = root.getJSONObject(i);
                int id = recipeJs.getInt("id");
                String recipeName = recipeJs.getString("name");
                int servings = recipeJs.getInt("servings");
                String image = recipeJs.getString("image");
                JSONArray ingredientsJs = recipeJs.getJSONArray("ingredients");
                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; j < ingredientsJs.length(); j++) {
                    JSONObject ingredientJs = ingredientsJs.getJSONObject(j);
                    double quantity = ingredientJs.getDouble("quantity");
                    String measure = ingredientJs.getString("measure");
                    String ingredientName = ingredientJs.getString("ingredient");
                    ingredients.add(new Ingredient(ingredientName, measure, quantity));
                }
                List<Step> steps = new ArrayList<>();
                JSONArray stepsJs = recipeJs.getJSONArray("steps");
                for (int j = 0; j < stepsJs.length(); j++) {
                    JSONObject stepJs = stepsJs.getJSONObject(j);
                    int stepId = stepJs.getInt("id");
                    String shortDescription = stepJs.getString("shortDescription");
                    String description = stepJs.getString("description");
                    String videoUrl = stepJs.getString("videoURL");
                    String thumbnailURL = stepJs.getString("thumbnailURL");
                    steps.add(new Step(stepId, shortDescription, description, videoUrl,
                            thumbnailURL));
                }
                recipes.add(new Recipe(id, recipeName,servings, image, ingredients, steps));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
