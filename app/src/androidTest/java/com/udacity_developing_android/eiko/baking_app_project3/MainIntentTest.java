package com.udacity_developing_android.eiko.baking_app_project3;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.Espresso.onView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainIntentTest {
    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule
            = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void checkAllExternalIntent(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(
                Activity.RESULT_OK, null));
    }
    @Test
    public void checkIntentRecipeDetail(){
        onView(ViewMatchers.withId(R.id.recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, click()));
                intended(hasExtra("", 2));
    }
}
