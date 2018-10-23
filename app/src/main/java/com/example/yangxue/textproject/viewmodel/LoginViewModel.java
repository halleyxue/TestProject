package com.example.yangxue.textproject.viewmodel;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.yangxue.textproject.HttpUtil;
import com.example.yangxue.textproject.global.Global;
import com.example.yangxue.textproject.model.JsonMsgOut;
import com.example.yangxue.textproject.model.User;

public class LoginViewModel extends BaseViewModel {

    protected User user = new User();
    protected Context context;
    public static final String loginUrl = "login.do";

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void login(String userName,String userPwd){
        user.setName(userName);
        user.setPassword(userPwd);
        HttpUtil.getInstance();
        HttpUtil.post(loginUrl,user,new LoginCallback(context));
    }

    class LoginCallback extends HttpUtil.HttpUtilCallback {

        public LoginCallback(Context context) {
            super(context);
        }

        @Override
        public void onSuccess(JsonMsgOut out) {
            if (out != null) {
                User loginBeanOut = JSON.parseObject(out.getObj(), User.class);
                viewModelData.state = ViewModelData.State.SUCCESS;
                viewModelData.object = loginBeanOut;
                Global.username = loginBeanOut.getName();
                data.setValue(viewModelData);
            }
        }

        @Override
        public void onError(JsonMsgOut error) {
            viewModelData.state = ViewModelData.State.FAIL;
            data.setValue(viewModelData);
        }
    }

}
