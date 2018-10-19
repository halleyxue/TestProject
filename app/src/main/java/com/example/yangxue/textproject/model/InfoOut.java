package com.example.yangxue.textproject.model;

import com.alibaba.fastjson.JSON;

public class InfoOut {
    private String obj;

    public String getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        if (obj instanceof String) {
            this.obj = String.valueOf(obj);
        } else {
            this.obj = JSON.toJSONString(obj);
        }
    }
}
