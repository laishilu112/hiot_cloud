package com.huatec.hiot_cloud.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huatec.hiot_cloud.R;
import com.huatec.hiot_cloud.mvptest.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag", "com/huatec/hiot_cloud/test");

    }

    private void login(User user) {
        if ("lisi".equals(user.getUserName()) && "123".equals(user.getPassword())) {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
    }else{
        Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
    }
}}