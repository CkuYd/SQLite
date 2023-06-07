package com.itcast.sqlite;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private Button btnChangePassword;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        databaseHelper = new DatabaseHelper(this);
//设置修改密码按钮的点击事件监听器，点击修改密码按钮时，会创建 DatabaseHelper 对象并调用 changePassword() 方法执行修改密码操作
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                // 调用 changePassword() 方法执行修改密码操作
                if (databaseHelper.changePassword(oldPassword, newPassword)) {
                    // 显示密码修改成功的提示信息
                    Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // 显示密码修改失败的提示信息
                    Toast.makeText(ChangePasswordActivity.this, "密码修改失败，请检查旧密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}