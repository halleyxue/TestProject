package com.example.yangxue.textproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

public class BaseViewModel extends AndroidViewModel{

    protected Context context;
    protected MutableLiveData<ViewModelData> data = new MutableLiveData<>();
    protected ViewModelData viewModelData = new ViewModelData();

    public BaseViewModel(Application application){
        super(application);
        this.context = application;
    }

    public MutableLiveData<ViewModelData> getViewModelData(){
        return  data;
    }
}
