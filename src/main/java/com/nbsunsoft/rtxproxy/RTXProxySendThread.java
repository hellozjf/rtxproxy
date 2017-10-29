package com.nbsunsoft.rtxproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbsunsoft.rtxproxy.domain.Message;
import com.nbsunsoft.rtxproxy.service.MessageService;
import com.nbsunsoft.rtxproxy.service.RTXService;
import com.nbsunsoft.rtxproxy.util.PropertiesUtils;

@Component("rtxProxySendThread")
public class RTXProxySendThread extends Thread {
    
    private static final Logger LOG = LoggerFactory.getLogger(RTXProxySendThread.class);

    private MessageService messageService;
    
    private RTXService rtxManager;
    
    @Override
    public void run() {
        try {
            String url = PropertiesUtils.getProperty("rtxproxy.sendUrl");
            String sendInterval = PropertiesUtils.getProperty("rtxproxy.sendInterval");
            int interval = Integer.valueOf(sendInterval);
            while (true) {
                Thread.sleep(interval);
                Message message = messageService.getLongestUnsendMessage();
                if (message != null) {
                    LOG.debug("will send message {}", message);
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
    public void setRtxManager(RTXService rtxManager) {
        this.rtxManager = rtxManager;
    }
}
