package demo.igorzor.tdump.ui.landing;

import android.support.test.espresso.Espresso;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import demo.igorzor.tdump.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LandingActivityTest {

    @Rule
    public ActivityTestRule<LandingActivity> activityActivityTestRule = new ActivityTestRule<LandingActivity>(LandingActivity.class);



    @Test
    public void checkMainScreenTitleIsSet() throws Exception{
        onView(withId(R.id.toolbar_title)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.app_name)));
    }

    @Test
    public void checkTagCloudFragmentIsDisplayed() throws Exception {
        onView(withId(R.id.tag_cloud_fragment_root)).check(matches(isDisplayed()));
    }

    @Test
    public void whenListFragmentIsDisplayedItShouldContainRecyclerview() {
        onView(withId(R.id.tag_cloud_fragment_root)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void whenRecyclerViewIsDisplayedCheckItContainsItems() {
        onView(withId(R.id.tag_cloud_fragment_root)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void whenClickingRecyclerViewItemItShouldLaunchDetailsFragment() {
        onView(withId(R.id.tag_cloud_fragment_root)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.tagcloud_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.quote_list_fragment_root)).check(matches(isDisplayed()));
    }

    @Test
    public void detailsFragmentShouldContainRecyclerview() {
        onView(withId(R.id.quote_list_fragment_root)).check(matches(isDisplayed()));
        onView(withId(R.id.quotelist_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.quotelist_rv)).check(matches(hasMinimumChildCount(1)));

    }

    @Test
    public void clickingBackFromDetailsViewShouldOpenTagCloud() {
        onView(withId(R.id.tag_cloud_fragment_root)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(isDisplayed()));
        onView(withId(R.id.tagcloud_rv)).check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.tagcloud_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.quote_list_fragment_root)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.tagcloud_rv)).check(matches(isDisplayed()));
    }


}