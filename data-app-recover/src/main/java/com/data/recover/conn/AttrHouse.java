package com.data.recover.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Victor on 17-7-1.
 */
public class AttrHouse {
    private static Logger logger = LoggerFactory.getLogger(AttrHouse.class);
    private static Properties properties;
    private static final String EVE_FILE = "application.properties";

    private AttrHouse() {
    }

    private static Properties getProperties() throws IOException {
        if (properties == null) {
            synchronized (AttrHouse.class) {
                if (properties == null) {
                    logger.info("start to load properties file");
                    properties = new Properties();
                    properties.load(AttrHouse.class.getClassLoader().getResourceAsStream(EVE_FILE));
                }
            }
        }
        return properties;
    }

    public static String getPropertiesValue(String key) {
        String object = null;
        try {
            object = getProperties().getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
