package com.udacity_developing_android.eiko.baking_app_project3.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.google.gson.Gson;

import com.udacity_developing_android.eiko.baking_app_project3.R;
import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.utils.RecipeJsonUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

public class ConfigRecipeWidget extends Activity {

    private static final String LOG = ConfigRecipeWidget.class
            .getSimpleName();
    private static final String PREF_NAME = "RecipeWidget";
    private static final String PREF_KEY = "recipeWidget";
    private static final String PRE_RECIPE_KEY = "currentRecipe";
    private static final HashMap<String, String>
            recipeHashmap = new HashMap<>();
    private static ArrayList<String> spinnerOption = new ArrayList<>();
    private static AppWidgetManager widgetManager;
    private static RemoteViews remoteViews;
    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Spinner recipeNameSpinner;
    Button appWidgetButton;
    private ArrayList<Recipe> recipeList;

    public ConfigRecipeWidget() {
        super();
    }

    public static void saveRecipePreference(Context context,
                                            int appWidgetId,
                                            String text,
                                            Recipe currentRecipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(
                PREF_NAME, 0).edit();
        prefs.putString(PRE_RECIPE_KEY + appWidgetId, text);
        Gson gson = new Gson();
        String json = gson.toJson(currentRecipe);
        prefs.putString(PRE_RECIPE_KEY + appWidgetId, json);
        prefs.apply();
    }

    public static Recipe getCurrentRecipePreference(Context context,
                                                    int appwidgetId) {
        SharedPreferences preferences = context.getSharedPreferences(
                PREF_NAME, 0);
        String jsoncurrent = preferences.getString(PRE_RECIPE_KEY +
                appwidgetId, null);
        Gson gsoncurrent = new Gson();
        Recipe currentRecipe = gsoncurrent.fromJson(jsoncurrent,
                Recipe.class);
        return currentRecipe;
    }

    public static void deleteRecipePreference(Context context,
                                              int appwidgetId) {
        SharedPreferences.Editor preferences = context.getSharedPreferences(
                PREF_NAME, 0).edit();
        preferences.remove(PRE_RECIPE_KEY + appwidgetId);
        preferences.apply();
    }

    public static String getRecipeDetailPreference(Context context,
                                                   int appWidgetId) {
        SharedPreferences preferences = context.getSharedPreferences(
                PREF_NAME, 0);
        String recipeDetail = preferences.getString(PRE_RECIPE_KEY +
                appWidgetId, null);
        return recipeDetail;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        setResult(RESULT_CANCELED);
        ButterKnife.bind(this);
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        final ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,
                        spinnerOption);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_list_item_activated_1);
        recipeNameSpinner.setAdapter(spinnerAdapter);

        new AsyncTask<Void, Void, ArrayList<Recipe>>() {

            @Override
            protected ArrayList<Recipe> doInBackground(Void... voids) {
                ArrayList<Recipe> recipeArrayList = null;
                try {
                    recipeArrayList = RecipeJsonUtil.getRecipe(
                            RecipeJsonUtil.RECIPE_URL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return recipeArrayList;
            }

            @Override
            protected void onPostExecute(ArrayList<Recipe> recipes) {
                recipeList = recipes;
                for (Recipe recipe : recipes) {
                    String recipeIngredientList = null;
                    spinnerOption.add(recipe.getName());
                    for (String ingredient : recipe.getIngredient()) {
                        if (recipeIngredientList == null) {
                            recipeIngredientList = ingredient;
                        } else {
                            recipeIngredientList = recipeIngredientList +
                                    ",\n" + ingredient;
                        }
                    }
                    recipeHashmap.put(recipe.getName(), recipeIngredientList);
                }
                spinnerAdapter.notifyDataSetChanged();
            }
        }.execute();

        widgetManager = AppWidgetManager.getInstance(this);
        remoteViews = new RemoteViews(this.getPackageName(),
                R.layout.recipe_widget_provider);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        appWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = null;
                String selectedRecipe = recipeNameSpinner.getSelectedItem()
                        .toString();
                for (Recipe r : recipeList) {
                    if (r.getName().equals(selectedRecipe)) {
                        recipe = r;
                    }
                }
                remoteViews.setTextViewText(R.id.widget_recipe_text_name,
                        selectedRecipe);
                remoteViews.setTextViewText(R.id.widget_recipe_ingredient_list,
                        recipeHashmap.get(selectedRecipe));
                String imageName = (selectedRecipe.replaceAll("\\s+"
                        , "")).toLowerCase();
                int imageResId = getApplicationContext().getResources().
                        getIdentifier(imageName, "drawable",
                                getApplicationContext().getPackageName());
                remoteViews.setImageViewResource(
                        R.id.widget_recipe_image, imageResId);

                saveRecipePreference(getApplicationContext(),
                        appWidgetId, selectedRecipe + ":" +
                                recipeHashmap.get(selectedRecipe), recipe);
            }
        });

    }
}
