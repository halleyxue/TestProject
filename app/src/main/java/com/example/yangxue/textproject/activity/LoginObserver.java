package com.example.yangxue.textproject.activity;

import com.example.yangxue.textproject.viewmodel.ViewModelData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LoginObserver implements Observer<ViewModelData>{
    private String result;
    private AppCompatActivity activity;

    public LoginObserver(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onChanged(@Nullable ViewModelData viewModelData) {
        if (ViewModelData.State.SUCCESS == viewModelData.state) {
            Intent intent = new Intent(activity,MainActivity.class);
            intent.putExtra("result",result);
            activity.startActivity(intent);
            activity.finish();
        } else if (ViewModelData.State.FAIL == viewModelData.state) {
            System.out.println("FAIL");
        }

    }
}
