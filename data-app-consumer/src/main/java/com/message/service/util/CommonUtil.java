package com.message.service.util;

/**
 * Created by victor on 2017/29/6.
 */
public class CommonUtil {
    public static final String NORMAL_MAPPER_XML_LOCATIONS = "classpath*:mapper/common/*.xml";
    public static final String NORMAL_MAPPER_SCAN_PACKAGE = "com.message.service.dao.common";
    public static final String NORMAL_ENVIRONMENT_PREFIX = "spring.datasource.normal";

    public static final String DATA_CENTER_MAPPER_XML_LOCATIONS = "classpath*:mapper/datacenter/*.xml";
    public static final String DATA_CENTER_MAPPER_SCAN_PACKAGE = "com.message.service.dao.datacenter";
    public static final String DATA_CENTER_ENVIRONMENT_PREFIX = "spring.datasource.datacenter";

    public static final String MYBATIS_CONFIG_FILE = "classpath:mybatis-config.xml";

}
