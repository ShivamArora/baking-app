package com.shivora.example.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shivora.example.bakingapp.models.Recipe;
import com.shivora.example.bakingapp.views.activities.RecipeListActivity;
import com.shivora.example.bakingapp.views.activities.RecipeStepsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityInstrumentedTest {

    public static final String TEXT_BROWNIES = "Brownies";
    public static final String TEXT_BROWNIE_INGREDIENTS = "5.0 UNIT large eggs";
    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityTestRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void checkRecipeData_isLoaded(){
        onView(withText(TEXT_BROWNIES)).check(matches(isDisplayed()));
    }

    @Test
    public void checkRecipeClick_showsDetails(){
        onView(withId(R.id.rv_recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withId(R.id.tv_ingredients)).check(matches(withText(containsString(TEXT_BROWNIE_INGREDIENTS))));
    }

    @Test
    public void checkRecipeClickIntent(){
        onView(withId(R.id.rv_recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        intended(
                allOf(
                        hasComponent(RecipeStepsActivity.class.getName()),
                        hasExtra(equalTo(RecipeListActivity.EXTRA_RECIPE_DETAILS),instanceOf(Recipe.class))
                ));
    }
}
