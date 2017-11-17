package com.nrf.eyemovies.common.http.base;

/**
 * Created by Administrator on 2017/11/9.
 * 解析类基类
 */

public class BaseEntity<T> {
    private static int SUCCESS_CODE=000;//成功的code
    private int code;
    private String msg;
    private T data;


    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
