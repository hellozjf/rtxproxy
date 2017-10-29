package com.nbsunsoft.rtxproxy.domain;

public class Config {

    private String serverIp;
    private String serverPort;
    private String sendUrl;
    private String sendInterval;
    public String getServerIp() {
        return serverIp;
    }
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
    public String getServerPort() {
        return serverPort;
    }
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
    public String getSendUrl() {
        return sendUrl;
    }
    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }
    public String getSendInterval() {
        return sendInterval;
    }
    public void setSendInterval(String sendInterval) {
        this.sendInterval = sendInterval;
    }
    
}
