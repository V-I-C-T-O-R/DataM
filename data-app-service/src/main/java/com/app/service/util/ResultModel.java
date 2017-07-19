package com.app.service.util;

/**
 * Created by Victor on 17-6-23.
 */
public class ResultModel {
    private Integer code;
    private String describe;
    private Object results;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }
}
