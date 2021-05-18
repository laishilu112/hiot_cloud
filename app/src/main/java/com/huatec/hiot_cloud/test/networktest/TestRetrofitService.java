package com.huatec.hiot_cloud.test.networktest;


import okhttp3.ResponseBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * retrofit service接口
 */
public interface TestRetrofitService {
    //public static final String basUrl = "http://14.215.177.39/";
    public static final String basUrl = "http://114.67.88.191:8080/";

    //百度
    @GET("/")
    Call<ResponseBody> test();

    //登录
    @POST("/auth/login")
    Call<ResponseBody> login(@Query("username") String userName, @Query("password") String password,
                             @Query("loginCode") String loginCode);


    //用户信息
    @GET("/user/one")
    Call<ResponseBody> getUserInfo(@Header("Authorization") String authorization);

    @GET("/user/one")
    Call<ResultBase<UserBean>> getUserInfo2(@Header("Authorization") String authorization);


    //修改邮箱
    @PUT("/user/email")
    Call<ResponseBody> updateEmail(@Header("Authorization") String authorization,
                                   @Query("email") String email);

    //注册
    @POST("/user/register")
    Call<ResponseBody> register(@Body UserBean userBean);
}

