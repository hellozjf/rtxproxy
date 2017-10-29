package com.nbsunsoft.rtxproxy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbsunsoft.rtxproxy.domain.Message;

@Service("rtxManager")
public class RTXService {
    
    private static final Logger LOG = LoggerFactory.getLogger(RTXService.class);

    public boolean sendToRTXServer(String url, Message message) throws Exception {
        // 从配置文件读IP地址
        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String title = message.getTitle();
        String msg = message.getMsg();
        String receiver = message.getReceiver();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("msg", msg));
        params.add(new BasicNameValuePair("receiver", receiver));

        httpRequst.setEntity(new UrlEncodedFormEntity(params, "gbk"));
        try {
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpRequst);
            LOG.debug("send to {}?title={}&msg={}&receiver={} success", url, title, msg, receiver);
//            LOG.debug("{}", httpResponse);
            return true;
        } catch (Exception e) {
            LOG.error("{}", e);
            return false;
        }
    }
}
