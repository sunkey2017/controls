package com.longi.controls.service;

/**
 * @version 1.0
 * @CalssName MessageService
 * @Author sunke5
 * @Date 2019-11-18 11:15
 */
public class MessageService {

    private String msg;

    public MessageService (String msg) {
        this.msg = msg;
    }
    public String getMsg () {
        return this.msg;
    }
    public void setMsg (String msg) {
        this.msg = msg;
    }
}
