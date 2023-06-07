package com.itcast.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnChangePassword;
    private Button btnViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnViewData = findViewById(R.id.btnViewData);
//设置修改密码按钮的点击事件监听器，跳转到修改密码页面
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到修改密码页面
                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
            }
        });
//设置查看数据按钮的点击事件监听器，跳转到查看数据页面，点击查看数据按钮时，会创建 DatabaseHelper 对象并调用 getAllData() 方法获取数据库中的信息
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据库中的信息并显示
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                String data = databaseHelper.getAllData();
                startActivity(new Intent(MainActivity.this, ViewDataActivity.class).putExtra("data", data));
            }
        });
    }
}