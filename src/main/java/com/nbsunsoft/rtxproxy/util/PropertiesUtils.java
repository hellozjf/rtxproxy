package com.nbsunsoft.rtxproxy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static String getProperty(String key) throws Exception {
        Properties properties = new Properties();
        InputStream in = new FileInputStream(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "rtxproxy.properties");
        properties.load(in);
        return properties.getProperty(key);
    }
}
