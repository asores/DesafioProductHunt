package com.producthuntpost;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.producthuntpost.ui.activity.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init(){
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }


    @Test
    public void testTransitionFragments(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000);
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(6));

            Thread.sleep(5000);
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(19));

            Thread.sleep(5000);
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(0));

            testClickCollection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testClickCollection() {
        try {
            Thread.sleep(3000);
            onView(withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            testFragmentPost();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void testFragmentPost(){
        onView(withId(R.id.recyclerViewPost)).check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
            onView(withId(R.id.recyclerViewPost))
                    .perform(RecyclerViewActions.scrollToPosition(6));

            Thread.sleep(3000);
            onView(withId(R.id.recyclerViewPost))
                    .perform(RecyclerViewActions.scrollToPosition(19));

            Thread.sleep(3000);
            onView(withId(R.id.recyclerViewPost))
                    .perform(RecyclerViewActions.scrollToPosition(0));


            testClickPost();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testClickPost() {
        try {
            Thread.sleep(3000);
            onView(withId(R.id.recyclerViewPost))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            testFragmentDetailsPost();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void testFragmentDetailsPost(){
        onView(withId(R.id.recycler_view_user_post)).check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
            onView(withId(R.id.recycler_view_user_post))
                    .perform(RecyclerViewActions.scrollToPosition(6));

            Thread.sleep(1000);
            onView(withId(R.id.recycler_view_user_post))
                    .perform(RecyclerViewActions.scrollToPosition(19));

            Thread.sleep(1000);
            onView(withId(R.id.recycler_view_user_post))
                    .perform(RecyclerViewActions.scrollToPosition(0));


            testClickDetailsPost();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testClickDetailsPost() {
        try {
            Thread.sleep(3000);
            onView(withId(R.id.text_send_details_creator)).check(matches(isDisplayed())).perform(click());
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
