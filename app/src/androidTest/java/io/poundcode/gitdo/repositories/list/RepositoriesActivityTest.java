package io.poundcode.gitdo.repositories.list;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.poundcode.gitdo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RepositoriesActivityTest {

    @Rule
    public ActivityTestRule<RepositoriesActivity> mActivityTestRule = new ActivityTestRule<>(RepositoriesActivity.class);

    @Test
    public void repositoriesActivityTest() {
        ViewInteraction floatingActionButton2 = onView(
            allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
            allOf(withId(R.id.repoName), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
            allOf(withId(R.id.user), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
            allOf(withId(R.id.user), isDisplayed()));
        appCompatEditText3.perform(replaceText("sirchip"));

        ViewInteraction floatingActionButton3 = onView(
            allOf(withId(R.id.search), isDisplayed()));
        floatingActionButton3.perform(click());

    }
}
