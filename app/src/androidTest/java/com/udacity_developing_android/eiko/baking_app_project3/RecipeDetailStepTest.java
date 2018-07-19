package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailStepTest {

    private RecipeStep recipeStep = new RecipeStep("1", "Intent Step: ",
            "Description: ", null, null);

    private String ingredient = "ingredient";
    private ArrayList<RecipeStep> stepList = new ArrayList<>(
            Arrays.asList(recipeStep));
    private ArrayList<String> ingredientList = new ArrayList<>(
            Arrays.asList(ingredient));
    private Recipe recipe = new Recipe("1", "Brownie", null, null,
            ingredientList,stepList);

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityActivityTestRule
            = new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class){
        @Override
        protected Intent getActivityIntent(){
            Intent intent = new Intent(
                    InstrumentationRegistry.getContext(), RecipeDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("RECIPE_DETAIL_INFORMATION", recipe);
            intent.putExtras(bundle);
            return intent;
        }
    };

    @Test
    public void clickRecipeStep(){
        onView(withId(R.id.recipe_step_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(
                        0, click()));
        onView(withId(R.id.step_description)).check(matches(withText(
                "step description")));
    }
}
