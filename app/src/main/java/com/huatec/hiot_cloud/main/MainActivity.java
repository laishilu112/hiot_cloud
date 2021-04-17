package com.huatec.hiot_cloud.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huatec.hiot_cloud.R;
import com.huatec.hiot_cloud.base.BaseActivity;
import com.huatec.hiot_cloud.base.BasePresenter;
import com.huatec.hiot_cloud.mvptest.model.User;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag", "com/huatec/hiot_cloud/test");

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }


}