package jp.kmats.android.okhttp_uitest_sample;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@RunWith(AndroidJUnit4.class)
public class OkHttpTest {

    private IdlingResource idlingResource;

    private LogRecorder logRecorder = new LogRecorder();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup() {
        // set logger to client through interceptor for buffering log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logRecorder);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClientHolder.initialize(client);

        // wait until response returns
        idlingResource = OkHttp3IdlingResource.create("OkHttp", client);
        registerIdlingResources(idlingResource);

        // launch MainActivity
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);
    }

    @After
    public void tearDown() {
        unregisterIdlingResources(idlingResource);
    }

    @Test
    public void checkOkHttp() {
        try {
            onView(withId(R.id.button)).perform(click());
        } catch (NoMatchingViewException e) {
            // do something
        }

        String url = "http://example.com/";
        logRecorder.assertLogMatch("--> GET " + url + " http/1.1");
        logRecorder.assertLogMatch("<-- 200 OK " + url + " \\(\\d+ms, .* body\\)");
        logRecorder.assertNoMoreLogs();
    }
}
