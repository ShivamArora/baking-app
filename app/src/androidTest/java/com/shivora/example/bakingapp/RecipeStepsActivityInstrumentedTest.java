package com.shivora.example.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivora.example.bakingapp.views.activities.RecipeListActivity;
import com.shivora.example.bakingapp.views.activities.RecipeStepsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeStepsActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void launchStepDetails(){
        onView(withId(R.id.rv_recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
    }

    @Test
    public void clickStep_showsInstructions(){
        onView(withId(R.id.tv_instructions)).check(matches(isDisplayed()));
    }

    @Test
    public void checkExoPlayer_isDisplayed(){
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    @Test
    public void checkNextButton_isEnabledAndDisplayed(){
        onView(withId(R.id.fab_next)).check(matches(isEnabled()));
        onView(withId(R.id.fab_next)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPreviousButton_isEnabledAndDisplayed(){
        onView(withId(R.id.fab_previous)).check(matches(isEnabled()));
        onView(withId(R.id.fab_previous)).check(matches(isDisplayed()));
    }
}
