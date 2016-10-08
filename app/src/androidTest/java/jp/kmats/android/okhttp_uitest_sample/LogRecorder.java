package jp.kmats.android.okhttp_uitest_sample;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static okhttp3.internal.platform.Platform.INFO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

// from https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/test/java/okhttp3/logging/HttpLoggingInterceptorTest.java#L676
public class LogRecorder implements HttpLoggingInterceptor.Logger {

    private final List<String> logs = new ArrayList<>();

    private int index;

    public LogRecorder assertLogEqual(String expected) {
        assertTrue("No more messages found", index < logs.size());
        String actual = logs.get(index++);
        assertThat(actual, is(expected));
        return this;
    }

    public LogRecorder assertLogMatch(String pattern) {
        assertTrue("No more messages found", index < logs.size());
        String actual = logs.get(index++);
        String reason = "<" + actual + "> did not match pattern <" + pattern + ">";
        assertThat(reason, Pattern.matches(pattern, actual), is(true));
        return this;
    }

    public void assertNoMoreLogs() {
        String reason = "More messages remain: " + logs.subList(index, logs.size());
        assertThat(reason, index == logs.size(), is(true));
    }

    @Override
    public void log(String message) {
        // preserve log for test
        logs.add(message);
        // output log to logcat
        // see the implementation of HttpLoggingInterceptor.Logger
        Platform.get().log(INFO, message, null);
    }
}
