package com.d4ve10.djremote.view;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.d4ve10.djremote.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testElementsAreDisplayed(){
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.host_button)).check(matches(isDisplayed()));
        onView(withId(R.id.host_button)).check(matches(withText("Host Music Room")));
        onView(withId(R.id.join_button)).check(matches(isDisplayed()));
        onView(withId(R.id.join_button)).check(matches(withText("Join Music Room")));
    }

    @Test
    public void testStartHostMusicRoomButton(){
        onView(withId(R.id.host_button)).perform(click());
        onView(withId(R.id.host_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void testJoinMusicRoomButton(){
        onView(withId(R.id.join_button)).perform(click());
        onView(withId(R.id.join_activity)).check(matches(isDisplayed()));
    }
}