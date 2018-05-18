package io.github.maxcruz.currencyapp.rates;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.maxcruz.currencyapp.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void splashShouldComplete() {
        // TODO: FIX BUD WITH ESPRESSO
    }
}