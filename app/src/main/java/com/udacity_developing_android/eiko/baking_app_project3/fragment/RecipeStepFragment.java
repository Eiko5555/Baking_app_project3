package com.udacity_developing_android.eiko.baking_app_project3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment implements
        StepAdapter.StepAdapterOnclicklistner {

    private OnStepSelectedListener selectedListener;
    private Recipe recipe;

    @BindView(R.id.recipe_ingredient_recyclerview)
    RecyclerView recipeIngredientRecyclerView;
    @BindView(R.id.recipe_step_recyclerview)
    RecyclerView recipeStepRecyclerview;
    @BindView(R.id.recipe_name)
    TextView recipeNameTextView;
    @BindView(R.id.recipe_ingredient_title)
    TextView recipeHeading;
    @BindView(R.id.recipe_step_title)
    TextView stepHeading;
    @BindView(R.id.recipe_step_subtitle)
    TextView stepSubTitle;

    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;

    public interface OnStepSelectedListener {
        void onStepSelected(RecipeStep currentStep);
    }

    public RecipeStepFragment() {
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
        recipe = recipeBundle.getParcelable("RECIPE_DETAIL_INFORMATION");
        recipeNameTextView.setText(recipe.getName());
        recipeHeading.setText("INGREDIENTS");
        stepHeading.setText("STEPS");
        stepSubTitle.setText("Click step to see detail.");

        ArrayList<String> ingredientList = recipe.getIngredient();
        ArrayList<RecipeStep> recipeStepList = recipe.getSteps();

        ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
        stepAdapter = new StepAdapter(getContext(), recipeStepList, this);

        recipeIngredientRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        recipeStepRecyclerview.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        recipeIngredientRecyclerView.setAdapter(ingredientAdapter);
        recipeStepRecyclerview.setAdapter(stepAdapter);
        return rootview;
    }

    @Override
    public void onClick(RecipeStep currentStep) {
        selectedListener.onStepSelected(currentStep);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepSelectedListener) {
            selectedListener = (OnStepSelectedListener) getActivity();
        }
        Log.i("RecipestepFragment", "onAttatch");
    }
}
