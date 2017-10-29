package com.rocdev.android.bakingtime.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.rocdev.android.bakingtime.models.Ingredient;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.models.Step;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by piet on 28-10-17.
 *
 */

public class DbUtils {

    /********************Save (replace) recipes ************************************/

    public static int saveRecipes(Context context, List<Recipe> recipes) {
        int rowsInserted = 0;
        if (recipes != null && recipes.size() > 0) {
            ContentResolver contentResolver = context.getContentResolver();
            deleteAllRecipes(contentResolver);
            ContentValues[] contentValuesArray = new ContentValues[recipes.size()];
            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                ContentValues cv = new ContentValues();
                cv.put(RecipeColumns.RECIPE_ID, recipe.getId());
                cv.put(RecipeColumns.NAME, recipe.getName());
                cv.put(RecipeColumns.IMAGE_URL, recipe.getImage());
                cv.put(RecipeColumns.SERVINGS, recipe.getServings());
                contentValuesArray[i] = cv;
                saveIngredients(contentResolver, recipe);
                saveSteps(contentResolver, recipe);
            }
            rowsInserted = contentResolver.bulkInsert(RecipesProvider.Recipes.CONTENT_URI,
                    contentValuesArray);
        }
        return rowsInserted;
    }

    private static void deleteAllRecipes(ContentResolver contentResolver) {
        contentResolver.delete(RecipesProvider.Recipes.CONTENT_URI, null, null);
        contentResolver.delete(RecipesProvider.Ingredients.CONTENT_URI, null, null);
        contentResolver.delete(RecipesProvider.Steps.CONTENT_URI, null, null);
    }

    private static int saveIngredients(ContentResolver contentResolver, Recipe recipe) {
        ContentValues[] contentValuesArray;
        List<Ingredient> ingredients = recipe.getIngredients();
        int rowsInserted = 0;
        if (ingredients != null) {
            contentValuesArray = new ContentValues[ingredients.size()];
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                ContentValues cv = new ContentValues();
                cv.put(IngredientColumns.INGREDIENT_NAME, ingredient.getName());
                cv.put(IngredientColumns.MEASURE, ingredient.getMeasure());
                cv.put(IngredientColumns.QUANTITY, ingredient.getQuantity());
                cv.put(IngredientColumns.RECIPE_ID, recipe.getId());
                contentValuesArray[i] = cv;
            }
            rowsInserted = contentResolver.bulkInsert(RecipesProvider.Ingredients.CONTENT_URI,
                    contentValuesArray);
        }
        return rowsInserted;
    }

    private static int saveSteps(ContentResolver contentResolver, Recipe recipe) {
        ContentValues[] contentValuesArray;
        List<Step> steps = recipe.getSteps();
        int rowsInserted = 0;
        if (steps != null) {
            contentValuesArray = new ContentValues[steps.size()];
            for (int i = 0; i < steps.size(); i++) {
                Step step = steps.get(i);
                ContentValues cv = new ContentValues();
                cv.put(StepColumns.DESCRIPTION, step.getDescription());
                cv.put(StepColumns.RECIPE_ID, recipe.getId());
                cv.put(StepColumns.SHORT_DESCRIPTION, step.getShortDescription());
                cv.put(StepColumns.STEP_ID, step.getId());
                cv.put(StepColumns.THUMBNAIL_URL, step.getThumbnailUrl());
                cv.put(StepColumns.VIDEO_URL, step.getVideoUrl());
                contentValuesArray[i] = cv;
            }
            rowsInserted = contentResolver.bulkInsert(RecipesProvider.Steps.CONTENT_URI,
                    contentValuesArray);
        }
        return rowsInserted;
    }

    /********************Retrieve recipes ************************************/

    public static List<Recipe> getRecipes(Context context) {
        List<Recipe> recipes = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(RecipesProvider.Recipes.CONTENT_URI,
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int recipeId = cursor.getInt(cursor.getColumnIndex(RecipeColumns.RECIPE_ID));
                String recipeName = cursor.getString(cursor.getColumnIndex(RecipeColumns.NAME));
                int servings = cursor.getInt(cursor.getColumnIndex(RecipeColumns.SERVINGS));
                String recipeImage = cursor.getString(cursor.getColumnIndex(RecipeColumns.IMAGE_URL));
                List<Ingredient> ingredients = getIngredientsFromRecipe(contentResolver, recipeId);
                List<Step> steps = getStepsFromRecipe(contentResolver, recipeId);
                recipes.add(new Recipe(recipeId, recipeName, servings, recipeImage, ingredients,
                        steps));
            }
            cursor.close();
        }
        return recipes;
    }

    private static List<Ingredient> getIngredientsFromRecipe(ContentResolver contentResolver,
                                                             int recipeId ) {
        Cursor cursor = contentResolver.query(RecipesProvider.Ingredients.CONTENT_URI,
                null, IngredientColumns.RECIPE_ID + " = ? ",
                new String[]{"" + recipeId}, null
        );
        List<Ingredient> ingredients = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String ingredientName = cursor.getString(cursor.getColumnIndex(IngredientColumns
                        .INGREDIENT_NAME));
                String ingredientMeasure = cursor.getString(cursor.getColumnIndex(IngredientColumns
                        .MEASURE));
                double ingredientQuantity = cursor.getDouble(cursor.getColumnIndex(IngredientColumns
                        .QUANTITY));
                ingredients.add(new Ingredient(ingredientName, ingredientMeasure,
                        ingredientQuantity));
            }
            cursor.close();
        }
        return ingredients;
    }

    private static List<Step> getStepsFromRecipe(ContentResolver contentResolver, int recipeId ) {
        List<Step> steps = new ArrayList<>();
        Cursor cursor = contentResolver.query(RecipesProvider.Steps.CONTENT_URI,
                null, StepColumns.RECIPE_ID + " = ? ",
                new String[]{"" + recipeId}, StepColumns.STEP_ID
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int stepId = cursor.getInt(cursor.getColumnIndex(StepColumns.STEP_ID));
                String shortDescription = cursor.getString(cursor.getColumnIndex(StepColumns
                        .SHORT_DESCRIPTION));
                String description = cursor.getString(cursor.getColumnIndex(StepColumns
                        .DESCRIPTION));
                String videoUrl = cursor.getString(cursor.getColumnIndex(StepColumns
                        .VIDEO_URL));
                String thumNailUrl = cursor.getString(cursor.getColumnIndex(StepColumns
                        .THUMBNAIL_URL));
                steps.add(new Step(stepId, shortDescription, description, videoUrl, thumNailUrl));
            }
            cursor.close();
        }
        return steps;
    }


}
