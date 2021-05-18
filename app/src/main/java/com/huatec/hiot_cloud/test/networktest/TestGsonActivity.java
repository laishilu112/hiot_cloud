package com.huatec.hiot_cloud.test.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huatec.hiot_cloud.R;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class TestGsonActivity extends AppCompatActivity {

    private static final String TAG = "TestGsonActivity";
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);


        //json转对象
        Button btnFromJson = findViewById(R.id.btn_from_json);
        btnFromJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = gson.fromJson("{\n" +
                        "\t\"height\": 175,\n" +
                        "\t\"id\": 23\n" +
                        "\t\"graduated\": false,\n" +
                        "\t\"name\": \"李四\"\n" +
                        "}", Student.class);
                if (student != null) {
                    String str = String.format("姓名：%s，id: %s,身高：%d,是否毕业：%b", student.getName(), student.getId(), student.getHeigh(), student.isGraudated());
                    Toast.makeText(TestGsonActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //对象转json
        Button btnToJson = findViewById(R.id.btn_to_json);
        btnToJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student2 = new Student();
                student2.setHeigh(167);
                student2.setId(24);
                student2.setName("李四");
                student2.setGraudated(true);
                String json = gson.toJson(student2);
                Log.e(TAG, "onClick: " + json);
            }
        });

        //json转list
        Button btnGsonList = findViewById(R.id.btn_gson_list);
        btnGsonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = "[\n" +
                        "\t{\n" +
                        "\t\t\"height\": 165,\n" +
                        "\t\"id\": 25\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"张三\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"height\": 175,\n" +
                        "\t\"id\": 54\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"李四\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"height\": 177,\n" +
                        "\t\"id\": 35\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"王五\"\n" +
                        "\t}\n" +
                        "]";
                Type type = new TypeToken<List<Student>>() {
                }.getType();
                List<Student> studentList = gson.fromJson(json, type);
                String str = "";
                if (studentList != null || studentList.isEmpty()) {
                    for (Student student : studentList) {
                        str = str + String.format("姓名：%s，id:%s,身高：%d,是否毕业：%b", student.getName(), student.getId(), student.getHeigh(), student.isGraudated());
                    }
                    Toast.makeText(TestGsonActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //json转map
        Button btnGsonMap = findViewById(R.id.btn_gson_map);
        btnGsonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = "{\n" +
                        "\t\"1\":{\n" +
                        "\t\t\"height\": 170,\n" +
                        "\t\"id\": 33\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"张三\"\n" +
                        "\t},\n" +
                        "\t\"2\":{\n" +
                        "\t\t\"height\": 175,\n" +
                        "\t\"id\": 40\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"李四\"\n" +
                        "\t},\n" +
                        "\t\"3\":{\n" +
                        "\t\t\"height\": 180,\n" +
                        "\t\"id\": 64\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"王五\"\n" +
                        "\t}\n" +
                        "}";
                Type type = new TypeToken<Map<String, Student>>() {
                }.getType();
                Map<String, Student> map = gson.fromJson(json, type);
                String str = "";
                if (map != null) {
                    for (String key : map.keySet()) {
                        str = str + String.format("序号：%s，姓名：%s，id:%s,身高：%d,是否毕业：%b", key, map.get(key).getName(), map.get(key).getId(), map.get(key).getHeigh(), map.get(key).isGraudated());
                    }
                    Toast.makeText(TestGsonActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //json转嵌套对象
        Button btnGsonNest = findViewById(R.id.btn_gson_nest);
        btnGsonNest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = "{\n" +
                        "\t\"data\":\n" +
                        "\t{\n" +
                        "\t\t\"height\": 155,\n" +
                        "\t\"id\": 47\n" +
                        "\t\t\"graduated\": false,\n" +
                        "\t\t\"name\": \"张三\"\n" +
                        "\t},\n" +
                        "\t\"status\":1,\n" +
                        "\t\"msg\":\"正常\"\n" +
                        "}";
                Type type = new TypeToken<ResultBase<Student>>() {
                }.getType();
                ResultBase<Student> resultBase = gson.fromJson(json, type);
                String str = "";
                str = str + String.format("姓名：%s，id:%s, 身高：%d,是否毕业：%b", resultBase.data.getName(), resultBase.data.getId(), resultBase.data.getHeigh(), resultBase.data.isGraudated());
                Toast.makeText(TestGsonActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
