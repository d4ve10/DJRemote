package com.d4ve10.djremote.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class JoinActivityTest {

    @Rule
    public ActivityScenarioRule<JoinActivity> activityScenarioRule
            = new ActivityScenarioRule<>(JoinActivity.class);

    @Test
    public void testElementsAreDisplayed() {
        onView(withId(R.id.join_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.bluetooth_status)).check(matches(isDisplayed()));
        onView(withId(R.id.bluetooth_status)).check(matches(withText("")));
        onView(withId(R.id.join_device_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeviceConnecting() {
        onView(withId(R.id.join_device_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.bluetooth_status)).check(matches(withText("Connecting...")));
    }

    @Test
    public void testDeviceConnected() {
        onView(withId(R.id.join_device_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.media_activity)).check(matches(isDisplayed()));

    }
}