package com.d4ve10.djremote.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.d4ve10.djremote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HostActivityTest {

    @Rule
    public ActivityScenarioRule<HostActivity> activityScenarioRule
            = new ActivityScenarioRule<>(HostActivity.class);

    @Test
    public void testElementsAreDisplayed() {
        onView(withId(R.id.host_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.connection_status)).check(matches(isDisplayed()));
        onView(withId(R.id.connection_status)).check(matches(withText("Nobody is connected")));
        onView(withId(R.id.host_device_list)).check(matches(isDisplayed()));
        onView(withId(R.id.discover_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewDeviceConnect() {
        onView(withId(R.id.connection_status)).check(matches(withText("1 device is connected")));
    }
}