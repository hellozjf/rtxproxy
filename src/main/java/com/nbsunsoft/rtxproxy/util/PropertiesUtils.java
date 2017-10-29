package com.nbsunsoft.rtxproxy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static String getProperty(String key) throws Exception {
        Properties properties = getProperties();
        return properties.getProperty(key);
    }

    public static Properties getProperties() throws Exception {
        Properties properties = new Properties();
        InputStream in = new FileInputStream(getPropertiesFileName());
        properties.load(in);
        return properties;
    }

    public static String getPropertiesFileName() throws Exception {
        return System.getProperty("user.dir") + File.separator + "conf" + File.separator + "rtxproxy.properties";
    }
}
