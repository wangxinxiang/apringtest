package com.example.wang.entity;

import java.util.Date;

public class AccessToken {

    private String access_token;
    private Integer expires_in;
    private Date createdate;
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public Integer getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
