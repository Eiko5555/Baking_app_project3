package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity_developing_android.eiko.baking_app_project3.fragment.RecipeStepDetailFragment;
import com.udacity_developing_android.eiko.baking_app_project3.fragment.RecipeStepFragment;
import com.udacity_developing_android.eiko.baking_app_project3.Recipe;
import com.udacity_developing_android.eiko.baking_app_project3.RecipeStep;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeStepFragment.OnStepSelectedListener {

    private static Recipe recipe;
    private static boolean twoPane;
    private RecipeStep selectedStep;
    private RecipeStepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        Bundle extra = getIntent().getExtras();
        recipe = extra.getParcelable("RECIPE_DETAIL_INFORMATION");

        if (savedInstanceState != null) {
            selectedStep = savedInstanceState.getParcelable(
                    "selectedStep");
            twoPane = savedInstanceState.getBoolean(
                    "twoPane");
        } else {
            if (findViewById(R.id.step_detail_container) != null) {
                twoPane = true;
                selectedStep = recipe.getSteps().get(0);
            } else {
                twoPane = false;
            }
        }
        if (twoPane) {
            if (savedInstanceState == null) {
                stepDetailFragment = new RecipeStepDetailFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_container,
                                stepDetailFragment,
                                "Step video not saved");
            } else {
                stepDetailFragment = new RecipeStepDetailFragment();
                stepDetailFragment = (RecipeStepDetailFragment)
                        getSupportFragmentManager().getFragment(
                                savedInstanceState, "video saved");
            }
        }
    }
    public static RecipeStep navigateStep(String stepId) {
        RecipeStep recipeStep = null;
        ArrayList<RecipeStep> currentRecipeStep = recipe.getSteps();
        for (int stepIndex = 0;
             stepIndex < currentRecipeStep.size(); stepIndex++) {
            if (stepId.equals(String.valueOf(currentRecipeStep.size() - 1))) {
                recipeStep = null;
            } else if (stepId.equals(currentRecipeStep.get(stepIndex).getStepId())) {
                recipeStep = currentRecipeStep.get(stepIndex);
            } else if (Integer.parseInt(stepId) < stepIndex) {
                recipeStep = currentRecipeStep.get(stepIndex);
                break;
            }
        }
        return recipeStep;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selectedStep", selectedStep);
        outState.putBoolean("twoPane", twoPane);
    }

    @Override
    public void onStepSelected(RecipeStep currentStep) {
        if (twoPane) {
            selectedStep = currentStep;
            RecipeStepDetailFragment stepDetailFragment =
                    new RecipeStepDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        } else {
            Bundle extra = new Bundle();
            extra.putParcelable("CURRENT_RECIPE_STEP", currentStep);
            Intent recipeStepDetailActivityIntent =
                    new Intent(this, RecipeStepDetailActivity.class);
            recipeStepDetailActivityIntent.putExtras(extra);
            startActivity(recipeStepDetailActivityIntent);
        }
    }

    public RecipeStep getCurrentStep() {
        return selectedStep;
    }
}

