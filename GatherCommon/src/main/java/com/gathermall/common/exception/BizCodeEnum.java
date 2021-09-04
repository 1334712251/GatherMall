package com.gathermall.common.exception;

public enum BizCodeEnum {


    UNKNOWN_EXCEPTION(500,"系统未知异常"),
    VALID_EXCEPTION(400,"参数格式校验失败");

    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
