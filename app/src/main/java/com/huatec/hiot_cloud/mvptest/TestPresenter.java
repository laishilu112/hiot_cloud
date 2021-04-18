package com.huatec.hiot_cloud.mvptest;

import com.huatec.hiot_cloud.ui.base.BasePresenter;
import com.huatec.hiot_cloud.mvptest.dagger2test.ThirdObj;
import com.huatec.hiot_cloud.mvptest.model.User;
import javax.inject.Inject;
public class TestPresenter extends BasePresenter <TestView>{
    @Inject
    ThirdObj thirdObj;
    @Inject
    public TestPresenter() {
    }



    public void  login(User user) {
        thirdObj.ThirdAction();
        if ("lisi".equals(user.getUserName()) && "123".equals(user.getPassword())) {
            getView().showMessage("登录成功");

        } else {
            getView().showMessage("登陆失败");
        }
    }

}