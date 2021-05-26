package com.huatec.hiot_cloud.test.networktest;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huatec.hiot_cloud.R;
import com.huatec.hiot_cloud.data.NetworkService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestRxjavaActivity extends AppCompatActivity {

    private static final String TAG = "TestRxjavaActivity";
    private Retrofit retrofit;
    private EditText etToken;
    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxjava);

        //editText
        etToken = findViewById(R.id.et_rxjava_token);

        //创建retrofit
        createRetrofit();

        //登录
        Button btnLogin = findViewById(R.id.btn_rxjava_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login("clcl", "cll123");
            }
        });

        //用户信息
        Button btnUserInfo = findViewById(R.id.btn_rxjava_userinfo);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo(etToken.getText().toString());
            }
        });

        //修改邮箱
        Button btnUpdateEmail = findViewById(R.id.btn_rxjava_update_email);
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmail(etToken.getText().toString(), "99999211w1@qq.com");
            }
        });

        //注册
        Button btnRegister = findViewById(R.id.btn_rxjava_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    /**
     * 注册
     */
    private void register() {
        UserBean userBean = new UserBean();
        userBean.setUsername("jlkhkkk1");
        userBean.setEmail("8886661a@qq.com");
        userBean.setPassword("abc123");
        userBean.setUserType("1");
        Observable<ResultBase<UserBean>> observable = service.register(userBean);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultBase<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBase<UserBean> resultBase) {
                        Toast.makeText(TestRxjavaActivity.this, resultBase.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 修改邮箱
     *
     * @param Authorization
     * @param email
     */
    private void updateEmail(String Authorization, String email) {
        service.updateEmail(Authorization, email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultBase<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBase<String> resultBase) {
                        String str = resultBase.getMsg();
                        Toast.makeText(TestRxjavaActivity.this, str, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取用户信息
     *
     * @param authorization
     */
    private void getUserInfo(String authorization) {
        Observable<ResultBase<UserBean>> observable = service.getUserInfo(authorization);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultBase<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBase<UserBean> resultBase) {
                        if (resultBase != null && resultBase.getData() != null) {
                            UserBean userBean = resultBase.getData();
                            String str = String.format("用户:%s,email：%s", userBean.getUsername(), userBean.getEmail());
                            Toast.makeText(TestRxjavaActivity.this, str, Toast.LENGTH_SHORT).show();
                        } else if (resultBase != null && TextUtils.isEmpty(resultBase.getMsg())) {
                            Toast.makeText(TestRxjavaActivity.this, resultBase.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    private void login(String username, String password) {
        service.login(username, password, "app")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBase<LoginResultDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBase<LoginResultDTO> resultBase) {
                        if (resultBase != null && resultBase.getData() != null) {
                            LoginResultDTO loginResult = resultBase.getData();
                            Log.d(TAG, "onNext: " + loginResult.getTokenValue());
                            etToken.setText(loginResult.getTokenValue());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 创建retrofit
     */
    private void createRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(TestRetrofitService.basUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(NetworkService.class);

    }
}
