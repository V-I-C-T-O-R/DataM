package com.app.service.util;

/**
 * Created by Victor on 17-6-23.
 */
public enum StatusCode {
    SUCCESS(200, "请求成功"), FAILED(400, "请求类型错误"),EXCEPTION(401,"传递参数不匹配"),ERROR(500,"服务器异常"), ERROR_CONFIG(501,"服务器配置异常");

    private String desc;
    private Integer code;

    StatusCode(Integer code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
