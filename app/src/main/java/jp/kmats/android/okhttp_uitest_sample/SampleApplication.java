package jp.kmats.android.okhttp_uitest_sample;

import android.app.Application;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        initialize();
    }

    private void initialize() {
        OkHttpClientHolder.initialize();
    }
}
