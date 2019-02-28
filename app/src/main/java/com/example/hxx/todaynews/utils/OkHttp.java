package com.example.hxx.todaynews.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp {
    public static void OkHttpConnection(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
