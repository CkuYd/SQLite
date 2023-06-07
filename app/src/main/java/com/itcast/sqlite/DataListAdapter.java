package com.itcast.sqlite;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//声明类并继承自 RecyclerView.Adapter<DataListAdapter.DataViewHolder>，并实现其抽象方法
public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.DataViewHolder> {

    private Context context;
    private List<String> dataList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private DatabaseHelper databaseHelper;

    //定义构造函数
    public DataListAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        databaseHelper = new DatabaseHelper(context);
    }

    //实现方法
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

    //实现方法
    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, final int position) {
        String data = dataList.get(position);
        holder.txtData.setText(data);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });
    }

    //实现方法
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //实现 setClickListener() 方法，用于设置点击事件监听器
    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    //定义接口 ItemClickListener，用于处理列表项点击事件
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    //实现 deleteData() 方法，用于删除数据
    public void deleteData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        databaseHelper.deleteData(String.valueOf(position + 1)); // 注意：这里假设数据库中的ID从1开始，并且与列表项的位置对应
    }

    //定义内部类 DataViewHolder，继承自 RecyclerView.ViewHolder，用于持有列表项的视图元素
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txtData;
        ImageButton btnDelete;

        public DataViewHolder(View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.tvData);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
//这段代码实现了一个适配器类，用于将数据绑定到 RecyclerView 的列表项上，并在列表项中提供删除功能