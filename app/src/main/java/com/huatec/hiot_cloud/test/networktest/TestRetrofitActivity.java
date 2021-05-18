package com.huatec.hiot_cloud.test.networktest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huatec.hiot_cloud.R;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestRetrofitActivity extends AppCompatActivity {

    private static final String TAG = "TestRetrofitActivity";
    private TestRetrofitService service;
    private Retrofit retrofit;
    private EditText etToken;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);

        //取到edit_token
        etToken = findViewById(R.id.et_token_retrofit);
        //创建retrofit和service对象
        createRetrofit();


        //百度
        Button btnTest = findViewById(R.id.btn_retrofit_enqueue);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();

            }
        });

        //登录
        Button btnLogin = findViewById(R.id.btn_retrofit_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login2("clcl", "cll123", "app");
            }
        });

        //用户信息
        Button btnUserInfo = findViewById(R.id.btn_retrofit_userinfo);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo("a7190eff01fb4619925aa00a51b33780_493027dc0b7e421bad1c7daea06f0296_use");
            }
        });

        //修改邮箱
        final Button btnUpdateEmail = findViewById(R.id.btn_retrofit_update_email);
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail("a7190eff01fb4619925aa00a51b33780_493027dc0b7e421bad1c7daea06f0296_use",
                        "ddl@qq.com");
            }
        });

        //注册
        Button btnRegister = findViewById(R.id.btn_retrofit_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    /**
     * 注册用户
     */
    private void register() {
        UserBean userBean = new UserBean();
        userBean.setUsername("dddf");
        userBean.setEmail("ddddd@qq.com");
        userBean.setPassword("abc123");
        userBean.setUserType("1");
        Call<ResponseBody> call = service.register(userBean);
        callEnqueue(call);
    }

    /**
     * 修改邮箱
     *
     * @param authorization
     * @param email
     */
    private void updateEmail(String authorization, String email) {
        Call<ResponseBody> call = service.updateEmail(authorization, email);
        callEnqueue(call);
    }

    /**
     * 获取用户信息
     *
     * @param authorization
     */
    private void getUserInfo(String authorization) {
//        Call<ResponseBody> call = service.getUserInfo(authorization);
//        callEnqueueUserInfo(call);

        Call<ResultBase<UserBean>> call = service.getUserInfo2(authorization);
        call.enqueue(new Callback<ResultBase<UserBean>>() {
            @Override
            public void onResponse(Call<ResultBase<UserBean>> call, Response<ResultBase<UserBean>> response) {
                ResultBase<UserBean> resultBase = response.body();
                if (resultBase != null && resultBase.getData() != null) {
                    resultBase.getData();
                    String str = resultBase.getData().getUsername() + "," + resultBase.getData().getEmail();
                    Toast.makeText(TestRetrofitActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultBase<UserBean>> call, Throwable t) {

            }
        });
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @param loginCode
     */
    private void login(String userName, String password, String loginCode) {
        Call<ResponseBody> call = service.login(userName, password, loginCode);
        callEnqueueLogin(call);
    }

    /**
     * 另一种登录接口方式
     *
     * @param userName
     * @param password
     * @param loginCode
     */
    private void login2(String userName, String password, String loginCode) {
        Call<ResponseBody> call = service.login(userName, password, loginCode);
        callEnqueue(call);
    }

    private void test() {
        Call<ResponseBody> call = service.test();
        callEnqueue(call);
    }


    private void callEnqueue(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse" + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "onResponse" + e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailue:" + t.getMessage(), t);
            }
        });

    }

    private void callEnqueueLogin(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Log.d(TAG, "onResponse" + response.body().string());
                Type type = new TypeToken<ResultBase<LoginResultDTO>>() {
                }.getType();
                ResultBase<LoginResultDTO> loginResult = gson.fromJson(response.body().toString(), type);
                if (loginResult != null && loginResult.getData() != null) {
                    String token = loginResult.getData().getTokenValue();
                    etToken.setText(token);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailue:" + t.getMessage(), t);
            }
        });

    }

    private void callEnqueueUserInfo(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    Log.d(TAG, "onResponse" + response.body().string());
                    Type type = new TypeToken<ResultBase<UserBean>>() {
                    }.getType();
                    ResultBase<UserBean> resultBase = gson.fromJson(response.body().string(), type);
                    if (resultBase != null && resultBase.getData() != null) {
                        UserBean userBean = resultBase.getData();
                        String str = String.format("用户名：%s，密码：%s，email：%s，用户类型：%s", userBean.getUsername(), userBean.getPassword(), userBean.getEmail(), userBean.getUserType());
                        Toast.makeText(TestRetrofitActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
//                    if (resultBase != null && resultBase.getMsg() != null){
//                        Toast.makeText(TestRetrofitActivity.this, resultBase.getMsg(), Toast.LENGTH_SHORT).show();
//                    }
                } catch (IOException e) {
                    Log.e(TAG, "onResponse" + e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailue:" + t.getMessage(), t);
            }
        });

    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(TestRetrofitService.basUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(TestRetrofitService.class);

    }
}
