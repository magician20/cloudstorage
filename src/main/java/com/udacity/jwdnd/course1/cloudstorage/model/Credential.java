package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private Integer userid;

    public Credential(Integer credentialid, String url, String userName, String key, String password, Integer userid) {
        this.credentialId = credentialid;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Credential(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialid) {
        this.credentialId = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
