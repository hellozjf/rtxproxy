package com.nbsunsoft.rtxproxy.service;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbsunsoft.rtxproxy.dao.MessageDao;
import com.nbsunsoft.rtxproxy.domain.Message;

@Service("messageService")
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private MessageDao messageDao;

    public synchronized Integer pushMessage(Message message) {
        Integer id = messageDao.getMaxMessageId() + 1;
        message.setId(id);
        Instant instant = Instant.now();
        message.setGmtCreate(instant);
        message.setGmtModified(instant);
        message.setbSent(0);
        messageDao.insertMessage(message);
        return id;
    }
    
    public synchronized Message getLongestUnsendMessage() {
        Integer id = messageDao.getLongestUnsendMessageId();
        if (id == null || id == 0) {
            return null;
        }
        Message message = messageDao.getMessage(id);
        return message;
    }
    
    public synchronized Message getMessageById(Integer id) {
        return messageDao.getMessage(id);
    }
    
    public synchronized void updateMessageById(Integer id) {
        Message message = messageDao.getMessage(id);
        Instant instant = Instant.now();
        message.setGmtModified(instant);
        messageDao.updateMessage(message);
    }
    
    public synchronized void setSentById(Integer id) {
        Message message = messageDao.getMessage(id);
        message.setbSent(1);
        messageDao.updateMessage(message);
    }
    
    public synchronized void setUnsendById(Integer id) {
        Message message = messageDao.getMessage(id);
        message.setbSent(0);
        messageDao.updateMessage(message);
    }
    
    public synchronized void setUnsendByIds(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Message message = messageDao.getMessage(ids.get(i));
            message.setbSent(0);
            messageDao.updateMessage(message);
        }
    }
    
    public synchronized JSONArray sentUnsendByJSON(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray ids = jsonObject.getJSONArray("ids");
        for (int i = 0; i < ids.size(); i++) {
            Integer integer = ids.getInteger(i);
            setUnsendById(integer);
        }
        return ids;
    }
    
    public synchronized List<Message> listMessage() {
        return messageDao.listMessage();
    }
    
    public synchronized List<Message> listMessage(List<Integer> ids) {
        return messageDao.listMessage(ids);
    }
    
    public synchronized boolean writeJSONToSQLite(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String type = jsonObject.getString("type");
        JSONObject message = jsonObject.getJSONObject("message");
        String title = message.getString("title");
        String msg = message.getString("msg");
        JSONArray receivers = message.getJSONArray("receivers");
        for (int i = 0; i < receivers.size(); i++) {
            Message newMessage = new Message();
            newMessage.setTitle(title);
            newMessage.setMsg(msg);
            newMessage.setReceiver(receivers.getString(i));
            pushMessage(newMessage);
        }
        return true;
    }

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

}
