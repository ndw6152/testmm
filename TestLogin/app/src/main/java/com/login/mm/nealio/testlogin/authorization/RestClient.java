package com.login.mm.nealio.testlogin.authorization;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * Created by ndw6152 on 5/9/2017.
 */

public class RestClient {

    private static String TAG = "RestClient";
    private static OkHttpClient client = new OkHttpClient();
    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void get(String url, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(responseCallback);
    }

    public static void post(final String url, String postBody, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, postBody))
                .build();
        client.newCall(request).enqueue(responseCallback);
    }


}

