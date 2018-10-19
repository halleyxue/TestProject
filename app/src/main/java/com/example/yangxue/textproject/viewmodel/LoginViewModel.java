package com.example.yangxue.textproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yangxue.textproject.HttpUtil;
import com.example.yangxue.textproject.global.Global;
import com.example.yangxue.textproject.model.User;

import static com.example.yangxue.textproject.activity.LoginActivity.loginUrl;

public class LoginViewModel extends AndroidViewModel {

    protected User user = new User();
    protected Context context;
    protected MutableLiveData<ViewModelData> data = new MutableLiveData<>();
    public static ViewModelData viewModelData = new ViewModelData();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void login(String userName,String userPwd){
        user.setName(userName);
        user.setPassword(userPwd);
        HttpUtil.getInstance();
        HttpUtil.postDataWithParame(loginUrl,user,new LoginCallback(context));
    }

    class LoginCallback extends HttpUtil.HttpUtilCallback {

        public LoginCallback(Context context) {
            super(context);
        }

        @Override
        public void onSuccess(String out) {
            if (out != null) {
                System.out.println("服务器端返回内容：" + out);
                viewModelData.state = ViewModelData.State.SUCCESS;
                viewModelData.object = out;
                Global.username = out;
                data.setValue(viewModelData);
            }
        }

        @Override
        public void onError(String error) {
            viewModelData.state = ViewModelData.State.FAIL;
            data.setValue(viewModelData);
        }
    }

    public MutableLiveData<ViewModelData> getViewModelData() {
        return data;
    }

}
