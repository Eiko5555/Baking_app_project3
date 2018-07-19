package com.udacity_developing_android.eiko.baking_app_project3.utils;

import android.util.Log;

import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeJsonUtil {
    public static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_SERVING = "servings";
    public static final String RECIPE_IMAGE = "image";
    public static final String RECIPE_INGREDIENT = "ingredients";
    public static final String RECIPE_INGREDIENTS_QUANTITY = "quantity";
    public static final String RECIPE_INGREDIENTS_MEASURE = "measure";
    public static final String RECIPE_KEY_INGREDIENT = "ingredient";
    public static final String RECIPE_STEP = "steps";
    public static final String RECIPE_STEPS_ID = "id";
    public static final String RECIPE_STEPS_SHORTDESCRIPTION = "shortDescription";
    public static final String RECIPE_STEP_DESCRIPTION = "description";
    public static final String RECIPE_STEP_VIDEO_URL = "videoURL";
    public static final String RECIPE_STEP_THUMBNAIL = "thumbnailURL";

    public static ArrayList<Recipe> getRecipe(String requestUrl) throws
            JSONException {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        URL recipeRequestUrl = null;
        try {
            recipeRequestUrl = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (recipeRequestUrl == null) {
            return null;
        }
        try {
            String urlRespons =
                    NetworkUtil.getResponseFrimUrl(recipeRequestUrl);
            JSONArray recipeJsonArray = new JSONArray(urlRespons);

            for (int i = 0; i < recipeJsonArray.length(); i++) {
                ArrayList<String> recipeIngredientList = new ArrayList<String>();
                ArrayList<RecipeStep> recipeStepList =
                        new ArrayList<RecipeStep>();

                JSONObject recipeObject = (JSONObject) recipeJsonArray.get(i);

                String id = recipeObject.getString(RECIPE_ID);
                String name = recipeObject.getString(RECIPE_NAME);
                String serving = recipeObject.getString(RECIPE_SERVING);
                String image = recipeObject.getString(RECIPE_IMAGE);
                JSONArray ingredients = recipeObject.getJSONArray(
                        RECIPE_INGREDIENT);
                JSONArray steps = recipeObject.getJSONArray(RECIPE_STEP);
                Log.i("Json data: ", steps.toString());

                for (int p = 0; p < ingredients.length(); p++) {
                    JSONObject ingredientObject = (JSONObject)
                            ingredients.get(p);
                    String quantity = ingredientObject.getString(
                            RECIPE_INGREDIENTS_QUANTITY);
                    String measure = ingredientObject.getString(RECIPE_INGREDIENTS_MEASURE);
                    String ingredient = ingredientObject.getString(
                            RECIPE_KEY_INGREDIENT);

                    String ingredientInfo = quantity + " " + measure + " " + ingredient;
                    Log.i("Json data: ", ingredientInfo);

                    recipeIngredientList.add(ingredientInfo);
                }
                for (int t = 0; t < steps.length(); t++) {
                    JSONObject stepObject = (JSONObject) steps.get(t);

                    String stepId = stepObject.getString(RECIPE_STEPS_ID);
                    String stepShortDescription = stepObject.getString(
                            RECIPE_STEPS_SHORTDESCRIPTION);
                    String stepDescription = stepObject.getString(RECIPE_STEP_DESCRIPTION);
                    String stepVideoUrl = stepObject.getString(RECIPE_STEP_VIDEO_URL);
                    String stepThumbnailUrl = stepObject.getString(RECIPE_STEP_THUMBNAIL);
                    Log.i("RecipeJsonUtil: ", stepVideoUrl+ " "+ stepThumbnailUrl );
                    Log.i("Json data desc: ", stepDescription);
                    Log.i("Json data shortdes: ", stepShortDescription);

                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setStepId(stepId);
                    recipeStep.setShortDescription(stepShortDescription);
                    recipeStep.setDescription(stepDescription);
                    recipeStep.setVideoUrl(stepVideoUrl);
                    recipeStep.setThumbnailUrl(stepThumbnailUrl);

                    recipeStepList.add(recipeStep);
                }
                Recipe recipe = new Recipe(id, name, serving, image,
                        recipeIngredientList, recipeStepList);
                recipes.add(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
