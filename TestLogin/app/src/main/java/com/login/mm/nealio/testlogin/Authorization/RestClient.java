package com.login.mm.nealio.testlogin.Authorization;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ndw6152 on 5/9/2017.
 */

public class RestClient {

    private static String TAG = "RestClient";
    private static OkHttpClient client = new OkHttpClient();
    static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    public static void get(String url, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(responseCallback);
    }

    public static void post(final String url, String postBody, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();
        client.newCall(request).enqueue(responseCallback);
    }


}

