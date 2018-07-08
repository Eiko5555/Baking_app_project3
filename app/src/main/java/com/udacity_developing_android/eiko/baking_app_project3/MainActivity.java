package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity_developing_android.eiko.baking_app_project3.utils.NetworkUtil;
import com.udacity_developing_android.eiko.baking_app_project3.utils.RecipeJsonUtil;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
RecipeAdapter.RecipeAdapterOnClickHandler{

    public static final int NETWORK_RECIPE_LOADER_ID = 1;
    @BindView(R.id.recyclerview)
    RecyclerView recipeRecyclerView;
    @BindView(R.id.error_msg)
    TextView errorMessage;
    @BindView(R.id.loading)
    ProgressBar progressBar;
    RecipeAdapter recipeAdapter;

    LoaderManager.LoaderCallbacks<ArrayList<Recipe>> recipeLoader =
            new LoaderManager.LoaderCallbacks<ArrayList<Recipe>>() {

        @Override
                public Loader<ArrayList<Recipe>> onCreateLoader(
                        int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<Recipe>>
                    (MainActivity.this) {

                ArrayList<Recipe> recipeList;

                @Override
                protected void onStartLoading() {
                    if (recipeList != null){
                        deliverResult(recipeList);
                    }else {
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Recipe> loadInBackground() {
                    ArrayList<Recipe> listOfRecipe = null;
                    try{
                        if (NetworkUtil.
                                isNetworkConected(getContext())){
                            listOfRecipe = RecipeJsonUtil
                                    .getRecipe(RecipeJsonUtil.RECIPE_URL);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return listOfRecipe;
                }

                @Override
                public void deliverResult(ArrayList<Recipe> data) {
                    recipeList = data;
                    super.deliverResult(data);
                }
            };
        }

                @Override
                public void onLoadFinished(Loader<ArrayList<Recipe>>
                                                   loader,
                                           ArrayList<Recipe> data) {
//            progressBar.setVisibility(View.INVISIBLE);
            recipeAdapter.setRecipeData(data);
            if (data == null){
                showErrorMessage();
                progressBar.setVisibility(View.VISIBLE);
            }else {
                showRecipe();
            }
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Recipe>>
                                                  loader) { }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        int orientation = GridLayout.VERTICAL;
        GridLayoutManager layoutManager = new GridLayoutManager(
                this, 1, orientation, false);
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeAdapter = new RecipeAdapter(this, this);
        recipeRecyclerView.setAdapter(recipeAdapter);
        getSupportLoaderManager().initLoader(
                NETWORK_RECIPE_LOADER_ID, null, recipeLoader);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent recipeIntent = new Intent(
                this, RecipeDetailActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable("RECIPE_DETAIL_INFORMATION", recipe);
        recipeIntent.putExtras(recipeBundle);
        startActivity(recipeIntent);
    }

    private void showErrorMessage(){
        recipeRecyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void showRecipe(){
        recipeRecyclerView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }
}
