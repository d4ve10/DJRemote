package com.d4ve10.djremote.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.d4ve10.djremote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MediaActivityTest {

    @Rule
    public ActivityScenarioRule<MediaActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MediaActivity.class);

    @Test
    public void testElementsAreDisplayed() {
        onView(withId(R.id.media_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.music_image)).check(matches(isDisplayed()));
        onView(withId(R.id.track_title)).check(matches(isDisplayed()));
        onView(withId(R.id.artist)).check(matches(isDisplayed()));
        onView(withId(R.id.play_button)).check(matches(isDisplayed()));
        onView(withId(R.id.forward_button)).check(matches(isDisplayed()));
        onView(withId(R.id.skip_button)).check(matches(isDisplayed()));
        onView(withId(R.id.rewind_button)).check(matches(isDisplayed()));
        onView(withId(R.id.previous_button)).check(matches(isDisplayed()));
        onView(withId(R.id.volume_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.volume_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickPlay() {
        onView(withId(R.id.play_button)).perform(click());
    }
}