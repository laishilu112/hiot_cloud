package com.huatec.hiot_cloud.mvptest.model;

import  java.io.Serializable;

public class User implements Serializable {
    private String userName;

    public String getPassword() {
        return password;
    }

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
