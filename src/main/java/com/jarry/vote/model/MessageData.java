package com.jarry.vote.model;

import com.jarry.vote.util.Constant;

import java.io.Serializable;

/**
 * 同意封装消息类
 */
public class MessageData implements Serializable {
    private String message;//消息内容
    private String code;//返回码
    private Object data;//返回数据

    public MessageData() {
    }

    public MessageData(String message, String code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public MessageData(String message, String code) {
        this.message = message;
        this.code = code;

    }

    /**
     * 创建默认的成功信息
     *
     * @param data
     * @return
     */
    public static MessageData createSuccessMsg(String msg, Object data) {
        return new MessageData(msg, Constant.CODE_SUCCESS, data);
    }

    /**
     * 创建默认的成功信息
     *
     * @param data
     * @return
     */
    public static MessageData createSuccessMsg(Object data) {
        return new MessageData(Constant.MSG_SUCCESS, Constant.CODE_SUCCESS, data);
    }

    //
    public static MessageData createErrorMsg() {
        return new MessageData();
    }

    public static MessageData createDefErrorMsg() {
        return new MessageData(Constant.MSG_ERROR, Constant.CODE_ERROR);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
