package com.example.yangxue.textproject;

import android.content.Context;

import okhttp3.OkHttpClient;

public class HttpUtiltest {
    public static final String SERVER_HOST="http://222.178.68.122:9092/ydmbk_yangxue";
    private static OkHttpClient client;

    private HttpUtiltest(){
    }

    public static void getInstance(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.build();
    }
}
