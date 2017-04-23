package com.lanthanh.admin.icareapp.welcomepage;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.welcomepage.ChooseFragment;
import com.lanthanh.admin.icareapp.presentation.welcomepage.LogInFragment;
import com.lanthanh.admin.icareapp.presentation.welcomepage.SignUpFragment;
import com.lanthanh.admin.icareapp.presentation.welcomepage.WelcomeActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

/**
 * @author longv
 *         Created on 22-Apr-17.
 */
@RunWith(AndroidJUnit4.class)
public class LogInViewTest {
    private LogInFragment logInFragment;
    private SignUpFragment signUpFragment;
    private ChooseFragment chooseFragment;
    private WelcomeActivity welcomeActivity;

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class) {
        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            logInFragment = new LogInFragment();
            signUpFragment = new SignUpFragment();
            chooseFragment = new ChooseFragment();
            welcomeActivity = getActivity();
        }
    };

    @Test
    public void testLogInButton() {
        String USERNAME = "testUser";
        String PASSWORD = "testPassword";
        //Show login view
        welcomeActivity.showFragment(logInFragment);
        //As default
        //-----Username input must be empty
        onView(withId(R.id.si_username_input)).check(matches(withText("")));
        //-----Password input must be empty
        onView(withId(R.id.si_password_input)).check(matches(withText("")));
        //-----Login button must be disabled
        onView(withId(R.id.si_sign_in_button)).check(matches(not(isEnabled())));
    }
}
