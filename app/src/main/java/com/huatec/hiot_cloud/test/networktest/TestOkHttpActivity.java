package com.huatec.hiot_cloud.test.networktest;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huatec.hiot_cloud.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp框架搭建类
 */
public class TestOkHttpActivity extends AppCompatActivity {

 //   private static final String basUrl = "http://14.215.177.38";
    private static final String basUrl = "http://114.67.88.191:8080";
    private static final String TAG = "TestOkHttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ok_http);

        //execute方法测试
        Button btnExecute = findViewById(R.id.btn_okhttp_execute);
        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testExecute();

            }
        });
        //enqueue方法测试
        Button btnEnqueue = findViewById(R.id.btn_okhttp_enqueue);
        btnEnqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testEnqueue();
            }
        });

        //登陆测试
        Button bthLogin = findViewById(R.id.btn_okhttp_login);
        bthLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login("clcl","cll123","app");

            }
        });
        //获取用户信息
        Button bthGetUserInfo = findViewById(R.id.btn_okhttp_userinfo);
        bthGetUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo("a7190eff01fb4619925aa00a51b33780_b4fa533bd576437382b05650e299cc35_use");

            }
        });

        //修改邮箱
        Button bthUpdateEmail = findViewById(R.id.btn_okhttp_update_email);
        bthUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail("a7190eff01fb4619925aa00a51b33780_b4fa533bd576437382b05650e299cc35_use", "cl@qq.com");

            }
        });



    }

    /**
     * 修改邮箱
     * @param authorization
     * @param email
     */
    private void updateEmail(String authorization, String email) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new  FormBody.Builder().build();
        String url = basUrl +"/user/email?email="+email;
        Request request = new  Request.Builder().put(body).url(url).header("Authorization",authorization).build();
        callEnqueue(okHttpClient, request);
    }

    /**
     * 获取用户信息
     * @param authorization
     */

    private void getUserInfo(String authorization) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new  FormBody.Builder().build();
        String url = basUrl +"/user/one";
        Request request = new  Request.Builder().get().url(url).header("Authorization",authorization).build();
        callEnqueue(okHttpClient, request);
    }


    private void callEnqueue(OkHttpClient okHttpClient, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage(), e);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());

            }
        });
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param loginCode
     */

    private void Login(String userName, String password, String loginCode) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new  FormBody.Builder().build();
        String url = basUrl + String.format("/auth/login?username=%s&password=%s&loginCode=%s",
                userName,password,loginCode);
        Request request = new  Request.Builder().post(body).url(url).build();
        callEnqueue(okHttpClient, request);


    }


    /**
     * 测试execute方式
     */
    private void testExecute() {

        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new  Request.Builder().url(basUrl).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d(TAG, "run: "+response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "testExecute: "+e.getMessage(),e );
                }
            }
        }.start();
    }

    /**
     * 测试enqueue方式
     */
        private void testEnqueue() {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new  Request.Builder().url(basUrl).build();
            callEnqueue(okHttpClient, request);

        }


    }



