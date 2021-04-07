package com.huatec.hiot_cloud.mvptest;

import com.huatec.hiot_cloud.mvptest.model.User;

public class TestPresenter {

    private  TestView view;

    public TestPresenter(TestView view) {
        this.view = view;
    }
    public void  login(User user) {
        if ("lisi".equals(user.getUserName()) && "123".equals(user.getPassword())) {
            view.showMessage("登录成功");

        } else {
            view.showMessage("登陆失败");
        }
    }
}