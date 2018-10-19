package com.example.yangxue.textproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static final String SERVER_HOST="http://222.178.68.122:9092/ydmbk_yangxue";
    private static OkHttpClient client;

    //构建方法私有化，避免通过new的方式创建对象，而是通过getInstance获得单例对象
    private HttpUtil(){
    }

    public static OkHttpClient getInstance(){
        if (client == null)
        {
            synchronized (HttpUtil.class)
            {
                if (client == null)
                {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    public static void getDataAsync() {
        System.out.println("test--------------------------");
        Request request = new Request.Builder()
                .url("http://222.178.68.122:9092/ydmbk_yangxue/test.do")
//                .url("https://www.baidu.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error--------------");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("result","获取数据成功了");
                    Log.d("result","response.code()=="+response.code());
                    Log.d("result","response.body().string()=="+response.body().string());
                }
            }
        });
    }

    public static Call postDataWithParame(String url, Object obj,HttpUtilCallback callback) {
        FormBody.Builder form_builder = new FormBody.Builder();
        String jsonParam = JSON.toJSONString(obj);
        form_builder.add("jsonStr",jsonParam);
        //声明一个请求对象体
        RequestBody request_body = form_builder.build();
        //采用post的方式进行提交
        Request request = new Request.Builder().url(SERVER_HOST+url).post(request_body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public abstract static class HttpUtilCallback extends Handler implements Callback {

        public Context context;
        private int success = 0;
        private int fail = 1;

        public HttpUtilCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Bundle data = new Bundle();
            Message message = new Message();
            message.what = fail;
            data.putSerializable("data", "请求服务器失败！");
            message.setData(data);
            sendMessage(message);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String content = response.body().string();
            Message message = new Message();
            Log.d("result","response.body().string()=="+content);
            if (content == null || content.length() == 0) {
                message.what = fail;
            } else if(response.isSuccessful()){
                message.what = success;
            }
            Bundle data = new Bundle();
            data.putSerializable("data", content);
            message.setData(data);
            sendMessage(message);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String out = (String) data.getSerializable("data");
            if (msg.what == success) {
                onSuccess(out);
            } else if (msg.what == fail) {
                onError(out);
            }
        }

        public abstract void onSuccess(String out);

        public abstract void onError(String error);
    }

}
