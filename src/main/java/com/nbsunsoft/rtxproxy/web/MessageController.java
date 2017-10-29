package com.nbsunsoft.rtxproxy.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbsunsoft.rtxproxy.domain.Message;
import com.nbsunsoft.rtxproxy.service.MessageService;

@RestController
public class MessageController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    
    private MessageService messageService;
    
    @RequestMapping("sendMessage.do")
    public String sendMessage(Message message) {
        LOG.debug("message:{}", message);
        Integer id = messageService.pushMessage(message);
        Message m = messageService.getMessageById(id);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("reason", "");
        jsonObject.put("message", (JSONObject) JSON.toJSON(m));
        return jsonObject.toJSONString();
    }
    
    @RequestMapping("getAllMessages.do")
    public String getAllMessages() {
        LOG.debug("getAllMessages.do");
        List<Message> messages = messageService.listMessage();
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("reason", "");
        jsonObject.put("messages", (JSONArray) JSON.toJSON(messages));
        return jsonObject.toJSONString();
    }
    
    @RequestMapping("resendMessage.do")
    public String resendMessage(String ids) {
        JSONArray jsonIds = JSONArray.parseArray(ids);
        List<Integer> iids = jsonIds.toJavaList(Integer.class);
        LOG.debug("resendMessage.do ids={}", iids);
        messageService.setUnsendByIds(iids);
        List<Message> messages = messageService.listMessage(iids);
        LOG.debug("messages:{}", messages);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("reason", "");
        jsonObject.put("messages", (JSONArray) JSON.toJSON(messages));
        return jsonObject.toJSONString();
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}
