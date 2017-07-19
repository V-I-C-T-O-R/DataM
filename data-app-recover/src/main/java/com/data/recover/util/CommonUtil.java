package com.data.recover.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;

/**
 * Created by victor on 2017/29/6.
 */
public class CommonUtil {
    public static String RESULT_COMPLETE_SIGNAL = "ok";
    public static int LINE_LAUNCH = 50000;
    public static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
}
