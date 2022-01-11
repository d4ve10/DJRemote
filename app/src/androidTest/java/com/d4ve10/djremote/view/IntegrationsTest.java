package com.d4ve10.djremote.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.d4ve10.djremote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testConnectAndPause() {
        onView(withId(R.id.join_button)).perform(click());
        onView(withId(R.id.join_device_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.play_button)).perform(click());
        onView(withId(R.id.track_title)).check(matches(isDisplayed()));
    }
}
