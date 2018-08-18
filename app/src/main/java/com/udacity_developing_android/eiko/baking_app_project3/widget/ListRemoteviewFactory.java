package com.udacity_developing_android.eiko.baking_app_project3.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.print.PrinterId;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity_developing_android.eiko.baking_app_project3.R;
import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ListRemoteviewFactory extends RemoteViewsService{

    @Override
    public RemoteviewFactoryList onGetViewFactory(Intent intent) {
        return new RemoteviewFactoryList(this.getApplicationContext());
    }
}
    class RemoteviewFactoryList implements RemoteViewsService.RemoteViewsFactory {
    private Context mConext;
    private ArrayList<String> list = new ArrayList<>();
    private static String recipeIngredient = "";
    int id;
    private static final String DIVIDER = ":";

    public RemoteviewFactoryList(Context applicationContext){
        mConext = applicationContext;
    }
        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
//        list.addAll()
//            String recipewidgetDetail =
//                    ConfigRecipeWidget.getRecipeDetailPreference(mConext, id);
////            if (recipewidgetDetail.contains(":")){
//
//                recipeIngredient = recipewidgetDetail;
//                Log.i("factory", recipewidgetDetail);
//            }
//            ArrayList<String> list_array =
//                    new ArrayList<>(Arrays.asList(recipeIngredient));
        }
        static void widget(Context context, AppWidgetManager manegaer, int id){

        String detail = ConfigRecipeWidget.getRecipeDetailPreference(context, id);
            if (detail.contains(DIVIDER)){
                String[] parts = detail.split(DIVIDER);
                recipeIngredient = parts[1];
                Log.i("factory", recipeIngredient);
            }
    }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (list == null || list.isEmpty()) {
            return 0;
        }
        return list.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
        String string = ConfigRecipeWidget.getRecipeDetailPreference(mConext,id);
        Log.i("factory", string);
                String ingredient = list.get(position);
            Log.i("factory", recipeIngredient);
        RemoteViews view = new RemoteViews(mConext.getPackageName(),
                R.layout.widget_ingredient_list);
        view.setTextViewText(R.id.widget_recipe_ingredient_list, ingredient);
        Intent intent = new Intent();
        view.setOnClickFillInIntent(R.id.widget_recipe_ingredient_list, intent);
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

