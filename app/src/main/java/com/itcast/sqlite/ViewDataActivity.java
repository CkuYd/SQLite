package com.itcast.sqlite;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity implements DataListAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private DataListAdapter adapter;
    private List<String> dataList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//创建 DatabaseHelper 对象并初始化 dataList 列表
        databaseHelper = new DatabaseHelper(this);
        dataList = new ArrayList<>();
//查询数据库中的数据，并将数据添加到 dataList 列表中
        String data = databaseHelper.getAllData();
        if (!data.isEmpty()) {
            String[] rows = data.split("\n");
            for (String row : rows) {
                dataList.add(row);
            }
        }
//创建适配器 DataListAdapter 并设置其点击监听器
        adapter = new DataListAdapter(this, dataList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
//设置返回按钮的点击事件监听器，点击返回按钮时，会销毁当前页面
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //实现接口 DataListAdapter.ItemClickListener 中的 onItemClick() 方法，用于处理列表项点击事件
    @Override
    public void onItemClick(View view, int position) {
        // 点击列表项时的操作
        deleteData(position);
    }

    //实现 deleteData() 方法，用于删除数据
    private void deleteData(int position) {
        // 删除数据
        dataList.remove(position);
        adapter.notifyItemRemoved(position);

        // 更新数据库中的数据
        String username = getUsernameFromDataList(position);
        databaseHelper.deleteData(username);
    }

    //实现 getUsernameFromDataList() 方法，用于从列表项中获取用户名
    private String getUsernameFromDataList(int position) {
        String data = dataList.get(position);
        String[] parts = data.split(", ");
        String[] usernameParts = parts[0].split(": ");
        return usernameParts[1];
    }
}




