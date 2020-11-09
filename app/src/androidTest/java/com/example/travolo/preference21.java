package com.example.travolo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class preference21 {
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void preference20() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("270"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView4.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView5.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView6.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.bb), withText("확인"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView7 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)));
        recyclerView7.perform(actionOnItemAtPosition(7, click()));

        ViewInteraction recyclerView8 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView8.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView9 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView9.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView10 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView10.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction recyclerView11 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView11.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction recyclerView12 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView12.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction recyclerView13 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView13.perform(actionOnItemAtPosition(9, click()));

        ViewInteraction recyclerView14 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView14.perform(actionOnItemAtPosition(11, click()));

        ViewInteraction recyclerView15 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView15.perform(actionOnItemAtPosition(18, click()));

        ViewInteraction recyclerView16 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView16.perform(actionOnItemAtPosition(13, click()));

        ViewInteraction recyclerView17 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView17.perform(actionOnItemAtPosition(12, click()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.bb), withText("확인"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction recyclerView18 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)));
        recyclerView18.perform(actionOnItemAtPosition(11, click()));

        ViewInteraction recyclerView19 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView19.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView20 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView20.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView21 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView21.perform(actionOnItemAtPosition(14, click()));

        ViewInteraction recyclerView22 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView22.perform(actionOnItemAtPosition(12, click()));

        ViewInteraction recyclerView23 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView22.perform(actionOnItemAtPosition(19, click()));

        ViewInteraction recyclerView24 = onView(
                allOf(withId(R.id.recycler_area),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView22.perform(actionOnItemAtPosition(21, click()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.bb), withText("확인"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.bb), withText("확인"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton5.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
