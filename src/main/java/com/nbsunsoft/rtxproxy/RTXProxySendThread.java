package com.nbsunsoft.rtxproxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbsunsoft.rtxproxy.domain.Message;
import com.nbsunsoft.rtxproxy.manager.RTXManager;
import com.nbsunsoft.rtxproxy.service.MessageService;

@Component("rtxProxySendThread")
public class RTXProxySendThread extends Thread {
    
    private static final Logger LOG = LoggerFactory.getLogger(RTXProxySendThread.class);

    private MessageService messageService;
    
    private RTXManager rtxManager;
    
    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(System.getProperty("user.dir") + File.separator + "conf" + File.separator + "rtxproxy.properties");
            properties.load(in);
            String url = properties.getProperty("rtxproxy.url");
            String sendInterval = properties.getProperty("rtxproxy.sendInterval");
            int interval = Integer.valueOf(sendInterval);
            while (true) {
                Thread.sleep(interval);
                Message message = messageService.getLongestUnsendMessage();
                if (message != null) {
                    rtxManager.sendToRTXServer(url, message);
                    messageService.setSentById(message.getId());
                }
            }
        } catch (Exception e) {
            LOG.error("{}", e);
        } 
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setRtxManager(RTXManager rtxManager) {
        this.rtxManager = rtxManager;
    }
}
