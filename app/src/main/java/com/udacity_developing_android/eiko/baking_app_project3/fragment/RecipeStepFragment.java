package com.udacity_developing_android.eiko.baking_app_project3.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity_developing_android.eiko.baking_app_project3.IngredientAdapter;
import com.udacity_developing_android.eiko.baking_app_project3.R;
import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeStep;
import com.udacity_developing_android.eiko.baking_app_project3.StepAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment implements
        StepAdapter.StepAdapterOnclicklistner {

    RecyclerView recipeIngredientRecyclerView, recipeStepRecyclerView;
    TextView recipeNameTextView, recipeHeading, stepHeading, stepSubHeading;
    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;
    private OnStepSelectedListener selectedListener;
    private Recipe recipe;

    public RecipeStepFragment() {
    }

    @Override
    public void onClick(RecipeStep currentStep) {
        selectedListener.onStepSelected(currentStep);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutFragment = R.layout.recipe_step_fragment;
        final View rootview = inflater.inflate(layoutFragment,
                container, false);
        ButterKnife.bind(this, rootview);
        Bundle recipeBundle = getActivity().getIntent().getExtras();
        recipe = recipeBundle.getParcelable("RECIPE_DETAIL_INFO");
        recipeNameTextView.setText(recipe.getName());
        recipeHeading.setText("");
        stepHeading.setText("");
        stepSubHeading.setText("");

        ArrayList<String> ingredientList = recipe.getIngredient();
        ArrayList<RecipeStep> recipeStepList = recipe.getSteps();

        ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
        stepAdapter = new StepAdapter(getContext(), recipeStepList, this);

        recipeIngredientRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        recipeStepRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        recipeIngredientRecyclerView.setAdapter(ingredientAdapter);
        recipeStepRecyclerView.setAdapter(stepAdapter);
        return rootview;
    }

    public interface OnStepSelectedListener {
        void onStepSelected(RecipeStep currentStep);
    }
}
