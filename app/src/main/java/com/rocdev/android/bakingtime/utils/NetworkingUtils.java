package com.rocdev.android.bakingtime.utils;

import android.util.Log;

import com.rocdev.android.bakingtime.models.Ingredient;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by piet on 14-10-17.
 *
 */

public class NetworkingUtils {


    private static final String RECIPES_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String TAG = NetworkingUtils.class.getSimpleName();

    public static ArrayList<Recipe> fetchRecipes () {
        StringBuilder output = new StringBuilder();
        URL url = makeUrl(RECIPES_URL);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                output.append(scanner.next());
            } else {
                return null;
            }
        } catch (IOException ignored) {} finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.d(TAG, output.toString());
        return JsonUtils.extractRecipesFromJson(output.toString());
    }

    private static URL makeUrl(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
