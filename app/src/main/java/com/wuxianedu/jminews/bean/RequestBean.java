package com.wuxianedu.jminews.bean;

/**
 * Created by Hu131 on 2016/6/27.
 */
public class RequestBean {
    private String reason;  //返回结果字符串
    private ResultBean result; //结果
    private int error_code; //错误码

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
