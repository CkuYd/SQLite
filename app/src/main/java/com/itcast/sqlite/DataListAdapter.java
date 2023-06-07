package com.itcast.sqlite;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.DataViewHolder> {

    private Context context;
    private List<String> dataList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private DatabaseHelper databaseHelper;

    public DataListAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void deleteData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        databaseHelper.deleteData(String.valueOf(position + 1)); // 注意：这里假设数据库中的ID从1开始，并且与列表项的位置对应
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txtData;
        Button btnDelete;

        public DataViewHolder(View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.tvData);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}