package com.nbsunsoft.rtxproxy.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nbsunsoft.rtxproxy.domain.Config;
import com.nbsunsoft.rtxproxy.service.ConfigService;

@RestController
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    private ConfigService configService;

    @RequestMapping("getConfig.do")
    public String getConfig() {
        LOG.debug("getConfig");
        Config config = configService.readConfig();

        JSONObject jsonObject = new JSONObject();
        if (config == null) {
            jsonObject.put("success", false);
            jsonObject.put("reason", "");
        } else {
            jsonObject.put("success", true);
            jsonObject.put("reason", "");
            jsonObject.put("config", (JSONObject) JSON.toJSON(config));
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("setConfig.do")
    public String setConfig(Config config) {
        LOG.debug("setConfig config={}", config);
        boolean bRet = configService.writeConfig(config);

        JSONObject jsonObject = new JSONObject();
        if (bRet) {
            jsonObject.put("success", true);
            jsonObject.put("reason", "");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("reason", "");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("downloadConfig.do")
    public String downloadConfig(HttpServletResponse res) {
        LOG.debug("downloadConfig");
        
        boolean bRet = configService.downloadConfig(res);
        
        JSONObject jsonObject = new JSONObject();
        if (bRet) {
            jsonObject.put("success", true);
            jsonObject.put("reason", "");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("reason", "");
        }
        return jsonObject.toJSONString();
    }

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }
}
