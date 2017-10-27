package com.nbsunsoft.rtxproxy.manager;

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
public class RTXManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(RTXManager.class);

    public boolean sendToRTXServer(String url, Message message) throws Exception {
        // 从配置文件读IP地址
        HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", message.getTitle()));
        params.add(new BasicNameValuePair("msg", message.getMsg()));
        params.add(new BasicNameValuePair("receiver", message.getReceiver()));

        httpRequst.setEntity(new UrlEncodedFormEntity(params, "gbk"));
        try {
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpRequst);
            LOG.debug("{}", httpResponse);
            return true;
        } catch (Exception e) {
            LOG.error("{}", e);
            return false;
        }
    }
}
