package com.itcast.sqlite;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        databaseHelper = new DatabaseHelper(this);
        dataList = new ArrayList<>();

        // 查询数据库中的数据
        String data = databaseHelper.getAllData();
        if (!data.isEmpty()) {
            String[] rows = data.split("\n");
            for (String row : rows) {
                dataList.add(row);
            }
        }

        adapter = new DataListAdapter(this, dataList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        // 点击列表项时的操作
        deleteData(position);
    }

    private void deleteData(int position) {
        // 删除数据
        dataList.remove(position);
        adapter.notifyItemRemoved(position);

        // 更新数据库中的数据
        String username = getUsernameFromDataList(position);
        databaseHelper.deleteData(username);
    }

    private String getUsernameFromDataList(int position) {
        String data = dataList.get(position);
        String[] parts = data.split(", ");
        String[] usernameParts = parts[0].split(": ");
        return usernameParts[1];
    }
}




