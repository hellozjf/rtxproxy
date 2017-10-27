package com.nbsunsoft.rtxproxy.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbsunsoft.rtxproxy.domain.Message;
import com.nbsunsoft.rtxproxy.service.MessageService;

@RestController
public class MessageController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    
    private MessageService messageService;
    
//    @RequestMapping("/{id}")
//    public String getMessage(@PathVariable("id") Integer id) {
//        LOG.debug("id:{}", id);
//        Message message = messageService.getMessageById(id);
//        return JSON.toJSONString(message);
//    }
//    
//    @RequestMapping("/all")
//    public String getAllMessage() {
//        List<Message> messages = messageService.listMessage();
//        return JSON.toJSONString(messages);
//    }
//    
//    @RequestMapping("/add")
//    public String addMessage(Message message) {
//        Integer id = messageService.pushMessage(message);
//        Message m = messageService.getMessageById(id);
//        return JSON.toJSONString(m);
//    }
    
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
        List<Message> messages = messageService.listMessage();
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("reason", "");
        jsonObject.put("messages", (JSONArray) JSON.toJSON(messages));
        return jsonObject.toJSONString();
    }
    
    @RequestMapping("resendMessage.do")
    public String resendMessage(Message message) {
        Integer id = message.getId();
        messageService.setUnsendById(id);
        Message m = messageService.getMessageById(id);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("reason", "");
        jsonObject.put("message", (JSONObject) JSON.toJSON(m));
        return jsonObject.toJSONString();
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}
