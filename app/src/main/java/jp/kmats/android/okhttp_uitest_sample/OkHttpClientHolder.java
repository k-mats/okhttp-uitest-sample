package jp.kmats.android.okhttp_uitest_sample;

import okhttp3.OkHttpClient;

public class OkHttpClientHolder {

    private static OkHttpClient client;

    public static void initialize() {
        client = new OkHttpClient.Builder().build();
    }

    public static void initialize(OkHttpClient client) {
        OkHttpClientHolder.client = client;
    }

    public static OkHttpClient getClient() {
        return client;
    }
}
