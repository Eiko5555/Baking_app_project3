package com.udacity_developing_android.eiko.baking_app_project3.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity_developing_android.eiko.baking_app_project3.R;

import java.util.ArrayList;

public class ListRemoteviewFactory extends RemoteViewsService {

    @Override
    public RemoteviewFactoryList onGetViewFactory(Intent intent) {
        return new RemoteviewFactoryList(this.getApplicationContext());
    }
}

class RemoteviewFactoryList implements RemoteViewsService.RemoteViewsFactory {

    private static final String PREF_NAME = "RecipeWidget";
    private static final String PREF_KEY = "recipeWidget";
    private static String recipeIngredient = "";
    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Context mConext;
    private ArrayList<String> list = new ArrayList<>();

    public RemoteviewFactoryList(Context applicationContext) {
        mConext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        Log.i("string", recipeIngredient);
        SharedPreferences file = mConext.getSharedPreferences(PREF_NAME, 0);
        String s = file.getString("KONDATE", "");
        String s2 = file.getString("zairyou", "");
        Log.i("listfactory1", s);
        Log.i("listfactory0", s2);
        if (s2 != null) {
            list.clear();
            list.add(s2);
        } else if (s != null) {
            list.clear();
            list.add(s);
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
        Log.i("factory1", list.toString());
        String ingredient = list.get(position);
        RemoteViews view = new RemoteViews(mConext.getPackageName(),
                R.layout.widget_ingredient_list);
        view.setTextViewText(R.id.widget_recipe_ingredient_list, ingredient);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
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

