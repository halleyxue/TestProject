package com.example.yangxue.textproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.example.yangxue.textproject.global.Global;
import com.example.yangxue.textproject.model.JsonMsgOut;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

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

    public static Call post(String url, Object obj,HttpUtilCallback callback) {
        FormBody.Builder form_builder = new FormBody.Builder();
        String jsonParam = JSON.toJSONString(obj);
        form_builder.add("jsonStr",jsonParam);
        //声明一个请求对象体
        RequestBody request_body = form_builder.build();
        //采用post的方式进行提交
        Request request = new Request.Builder().url(Global.SERVER_HOST+url).post(request_body).build();
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
            JsonMsgOut out = new JsonMsgOut();
            out.setErrorInfos(-1,context.getString(R.string.request_server_error));
            Bundle data = new Bundle();
            Message message = new Message();
            message.what = fail;
            data.putSerializable("data", out);
            message.setData(data);
            sendMessage(message);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String content = response.body().string();
            Message message = new Message();
            Log.d("result","response.body().string()=="+content);
            JsonMsgOut out = JSON.parseObject(content,JsonMsgOut.class);
            if (out == null || content.length() == 0) {
                out = new JsonMsgOut();
                out.resultCode = -1;
                out.errorInfos = out.new ErrorInfo();
                out.errorInfos.errorMsg = context.getString(R.string
                        .request_server_error);
            }
            if (out.resultCode == 0) {
                message.what = success;
            }else {
                message.what = fail;
            }
//            if(response.isSuccessful()){
//                message.what = success;
//            }
            Bundle data = new Bundle();
            data.putSerializable("data", out);
            message.setData(data);
            sendMessage(message);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            JsonMsgOut out = (JsonMsgOut) data.getSerializable("data");
            if (msg.what == success) {
                onSuccess(out);
            } else if (msg.what == fail) {
                onError(out);
            }
        }

        public abstract void onSuccess(JsonMsgOut out);

        public abstract void onError(JsonMsgOut error);
    }

}
