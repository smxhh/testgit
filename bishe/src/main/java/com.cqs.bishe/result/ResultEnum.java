package com.cqs.bishe.result;

import com.alibaba.fastjson.JSON;

/**
 * Created by cqs on 16-6-5.
 */
public enum ResultEnum{

    // normal
    SUCCESS(0,"success"),
    CHECK_ERROR(1,"check error"),
    INSERT_ERROR(2,"error"),
    VCODE_CHECK_ERROR(3,"vcode check error"),
    PWD_CHECK_ERROR(4,"pwd check error"),
    NORMAL_LOGIN_SUCCESS(5,"normal login success"),
    REGISTER_SUCCESSS(13,"register success"),

    // admin
    ADMIN_LOGIN_SUCCESS(6,"admin login success"),
    ADMIN_START_FUND_SUCCESS(7,"admin_start_fund_success"),
    ADMIN_STOP_FUND_SUCCESS(8,"admin_stop_fund_success"),
    ADMIN_ADD_FUND_SUCCESS(9,"admin_add_fund_success"),
    NOT_ADMIN(10,"not_admin"),
    ADMIN_START_FUND_ERROR(11,"admin_start_fund_error"),
    ADMIN_STOP_FUND_ERROR(12,"ADMIN_STOP_FUND_ERROR"),
    ADMIN_REGISTER_SUCCESSS(14,"admin_register_success");



    ;
    private int id;
    private String msg;


    ResultEnum( int id,String msg) {
        this.msg = msg;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static boolean isSuccess(ResultEnum result){
        return result == SUCCESS;
    }

    public static boolean isLoginSuccess(ResultEnum resultEnum){
        return resultEnum == ADMIN_LOGIN_SUCCESS || resultEnum == NORMAL_LOGIN_SUCCESS;
    }

    @Override
    public String toString() {
        return "ResultEnum{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String toJsonString(){
        return "{" +
                "\"id\":" + id +
                ",\"msg\":\"" + msg + '\"' +
                '}';
    }
}
