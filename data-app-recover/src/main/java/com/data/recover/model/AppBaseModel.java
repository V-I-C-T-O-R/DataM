package com.data.recover.model;

/**
 * Created by Victor on 17-6-30.
 */
public class AppBaseModel {

    private Long pid;
    private Integer storeId;
    private Integer regionId;
    private Long t;
    private Integer day;
    private Integer eventId;
    private Integer screenId;
    private String type;
    private String typeValues;
    private String extraType;
    private String extraValues;
    private String customType;
    private String customValue;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeValues() {
        return typeValues;
    }

    public void setTypeValues(String typeValues) {
        this.typeValues = typeValues;
    }

    public String getExtraType() {
        return extraType;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public String getExtraValues() {
        return extraValues;
    }

    public void setExtraValues(String extraValues) {
        this.extraValues = extraValues;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getCustomValue() {
        return customValue;
    }

    public void setCustomValue(String customValue) {
        this.customValue = customValue;
    }
}
