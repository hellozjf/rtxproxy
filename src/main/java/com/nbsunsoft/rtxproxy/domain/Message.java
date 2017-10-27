package com.nbsunsoft.rtxproxy.domain;

import java.time.Instant;

public class Message {

    private Integer id;
	private Instant gmtCreate;
	private Instant gmtModified;
	private String title;
	private String msg;
	private String receiver;
	private Integer bSent;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Instant getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(Instant gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public Instant getGmtModified() {
        return gmtModified;
    }
    public void setGmtModified(Instant gmtModified) {
        this.gmtModified = gmtModified;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public Integer getbSent() {
        return bSent;
    }
    public void setbSent(Integer bSent) {
        this.bSent = bSent;
    }
    @Override
    public String toString() {
        return "Message [id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", title=" + title
                + ", msg=" + msg + ", receiver=" + receiver + ", bSent=" + bSent + "]";
    }
	
}
