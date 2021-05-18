package com.huatec.hiot_cloud.test.networktest;

import android.text.TextUtils;

import com.huatec.hiot_cloud.data.DataManger;
import com.huatec.hiot_cloud.data.DataManger;
import com.huatec.hiot_cloud.ui.base.BasePresenter;

import javax.inject.Inject;

public class TestNetworkPresenter extends BasePresenter<TestNetworkPackView> {

    @Inject
    DataManger dataManager;

    @Inject
    public TestNetworkPresenter() {
    }

    public void login(String userName, String password) {
        subscrib(dataManager.login(userName, password), new RequestCallback<ResultBase<LoginResultDTO>>() {
            @Override
            public void onNext(ResultBase<LoginResultDTO> resultBase) {
                if (resultBase != null && resultBase.getData() != null) {
                    getView().showToken(resultBase.data.getTokenValue());
                } else if (resultBase != null && !TextUtils.isEmpty(resultBase.getMsg())) {
                    getView().shouMessage(resultBase.getMsg());
                }
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param authorization
     */
    public void getUserInfo(String authorization) {
        subscrib(dataManager.getUserInfo(authorization), new RequestCallback<ResultBase<UserBean>>() {
            @Override
            public void onNext(ResultBase<UserBean> resultBase) {
                if (resultBase != null && resultBase.getData() != null) {
                    UserBean userBean = resultBase.getData();
                    String str = String.format("用户:%s,email：%s", userBean.getUsername(), userBean.getEmail());
                    getView().shouMessage(str);
                } else if (resultBase != null && TextUtils.isEmpty(resultBase.getMsg())) {
                    getView().shouMessage(resultBase.getMsg());
                }
            }
        });
    }

    /**
     * 修改邮箱
     *
     * @param authorization
     * @param email
     */
    public void updateEmail(String authorization, String email) {
        subscrib(dataManager.updateEmail(authorization, email), new RequestCallback<ResultBase<String>>() {
            @Override
            public void onNext(ResultBase<String> resultBase) {
                if (resultBase != null && !TextUtils.isEmpty(resultBase.getData())) {
                    String newEmail = resultBase.getData();
                    getView().shouMessage("修改成功，新邮箱：" + newEmail);
                }
                if (resultBase != null && !TextUtils.isEmpty(resultBase.getMsg())) {
                    getView().shouMessage(resultBase.getMsg());
                }

            }
        });
    }

    /**
     * 注册
     *
     * @param userName
     * @param password
     * @param email
     */
    public void register(String userName, String password, String email) {
        subscrib(dataManager.register(userName, password, email), new RequestCallback<ResultBase<UserBean>>() {
            @Override
            public void onNext(ResultBase<UserBean> resultBase) {
                if (resultBase != null && resultBase.getData() != null) {
                    UserBean newUserBean = resultBase.getData();
                    String userStr = String.format("userName:%s, email:%s", newUserBean.getUsername(), newUserBean.getEmail());
                    getView().shouMessage(userStr);
                }
                if (resultBase != null && !TextUtils.isEmpty(resultBase.getMsg())) {
                    getView().shouMessage(resultBase.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().shouMessage(e.getMessage());
            }
        });
    }
}
