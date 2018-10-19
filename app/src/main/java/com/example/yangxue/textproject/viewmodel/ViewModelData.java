package com.example.yangxue.textproject.viewmodel;

import java.util.List;

public class ViewModelData {

    public enum State {
        NONE, SUCCESS, FAIL, YES, NO, RESUME, LOSE
    }

    public State state = State.NONE;
    public String errMsg;
    public Object object;
    public int total;
    public String key;

    public <T> T getObject() {
        return (T) object;
    }
}
