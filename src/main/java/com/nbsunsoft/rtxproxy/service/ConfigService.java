package com.nbsunsoft.rtxproxy.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbsunsoft.rtxproxy.domain.Config;
import com.nbsunsoft.rtxproxy.util.PropertiesUtils;

@Service
public class ConfigService {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigService.class);
    
    public static final String SERVER_IP = "rtxproxy.serverIp";
    public static final String SERVER_PORT = "rtxproxy.serverPort";
    public static final String SEND_URL = "rtxproxy.sendUrl";
    public static final String SEND_INTERVAL = "rtxproxy.sendInterval";

    public Config readConfig() {
        Config config = new Config();
        try {
            Properties properties = PropertiesUtils.getProperties();
            config.setServerIp(properties.getProperty(SERVER_IP));
            config.setServerPort(properties.getProperty(SERVER_PORT));
            config.setSendUrl(properties.getProperty(SEND_URL));
            config.setSendInterval(properties.getProperty(SEND_INTERVAL));
            return config;
        } catch (Exception e) {
            LOG.error("{}", e);
            return null;
        }
    }
    
    public boolean writeConfig(Config config) {
        try {
            Properties properties = PropertiesUtils.getProperties();
            properties.setProperty(SERVER_IP, config.getServerIp());
            properties.setProperty(SERVER_PORT, config.getServerPort());
            properties.setProperty(SEND_URL, config.getSendUrl());
            properties.setProperty(SEND_INTERVAL, config.getSendInterval());
            OutputStream out = new FileOutputStream(PropertiesUtils.getPropertiesFileName());
            properties.store(out, "");
            return true;
        } catch (Exception e) {
            LOG.error("{}", e);
            return false;
        }
    }
    
    public boolean downloadConfig(HttpServletResponse res) {
        String filePathName = null;
        try {
            filePathName = PropertiesUtils.getPropertiesFileName();
        } catch (Exception e1) {
            LOG.error("{}", e1);
            return false;
        }
        
        File tempFile =new File(filePathName.trim());  
        String fileName = tempFile.getName();  
        LOG.debug("filePathName={} fileName={}", filePathName, fileName);
//        System.out.println("fileName = " + filePathName); 
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(tempFile));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LOG.debug("downloadConfig success");
        return true;
    }
}
