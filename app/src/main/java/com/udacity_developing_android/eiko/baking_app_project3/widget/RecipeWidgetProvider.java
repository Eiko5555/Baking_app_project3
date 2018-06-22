package com.udacity_developing_android.eiko.baking_app_project3.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.udacity_developing_android.eiko.baking_app_project3.R;
import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeWidgetProvider extends AppWidgetProvider{

    private static final String DIVIDER = ":";
    private static String LOG = RecipeWidgetProvider.class.getSimpleName();
    private static String recipeName = "";
    private static String recipeIngredient = "";

    static void updateAppwidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appwidgetId){
        Recipe recipeAvailable = ConfigRecipeWidget.
                getCurrentRecipePreference(context, appwidgetId);
        String recipewidgetDetail = ConfigRecipeWidget.
                getRecipeDetailPreference(context, appwidgetId);

        if (recipewidgetDetail == null){
            return;
        }

        if (recipewidgetDetail.contains(DIVIDER)){
            String[] parts = recipewidgetDetail.split(DIVIDER);
            recipeName = parts[1];
            recipeIngredient = parts[1];
        }

        ArrayList<String> ingredientList = new ArrayList<>(Arrays.asList(
                recipeIngredient));
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.widget_recipe_text_name, recipeName);
        views.setTextViewText(R.id.widget_recipe_ingredient_list,
                recipeIngredient);

        String image = (recipeName.replaceAll("\\s+", ""))
                .toLowerCase();
        int imageResId = context.getResources().getIdentifier(
                image,"drawable", context.getPackageName());
        views.setImageViewResource(R.id.widget_recipe_image, imageResId);

        Bundle bundle = new Bundle();
        bundle.putParcelable("RECIPE_DETAIL_INFORMATION", recipeAvailable);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_recipe_card,
                pendingIntent);

        appWidgetManager.updateAppWidget(appwidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int appwidgetId : appWidgetIds)
        updateAppwidget(context, appWidgetManager, appwidgetId);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            ConfigRecipeWidget.deleteRecipePreference(
                    context, appWidgetId);
        }
    }
}
