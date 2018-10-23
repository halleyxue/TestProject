package com.example.yangxue.textproject.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class JsonMsgOut implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     * 0 没有错误
     * 1 警告，可以继续执行
     * -1 错误，程序错误不能继续执行
     */
    public int resultCode = 0;

    private String obj;
    public ErrorInfo errorInfos;

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

    public void setErrorInfos(int code, String message) {
        this.errorInfos = new ErrorInfo(code, message);
    }

    public class ErrorInfo {
        //错误码
        public int errorCode;
        //错误信息
        public String errorMsg;

        public ErrorInfo() {

        }

        public ErrorInfo(int code, String errorMsg) {
            this.errorCode = code;
            this.errorMsg = errorMsg;
        }
    }

}
