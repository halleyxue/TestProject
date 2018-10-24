package com.example.yangxue.textproject.global;

import android.app.Application;

import com.example.yangxue.textproject.HttpUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.getInstance();
    }
}
