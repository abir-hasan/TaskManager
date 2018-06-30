package com.example.abirhasan.finaltest.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.view_holders.DashBoardViewHolder;
import com.example.abirhasan.finaltest.ui.view_holders.HeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DashBoardAdapter extends ListAdapter<BaseTask, RecyclerView.ViewHolder> {
    private List<BaseTask> baseTaskList = new ArrayList<>();

    public DashBoardAdapter() {
        super(UserDiffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,
                parent, false);
        if (viewType == R.layout.adapter_header) {
            return new HeaderViewHolder(view);
        }
        return new DashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseTask baseTask = getItem(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(baseTask);
        } else {
            ((DashBoardViewHolder) holder).bind(baseTask);
        }
    }

    public void setData(List<BaseTask> tasks) {
        baseTaskList.clear();
        baseTaskList.addAll(tasks);
        submitList(tasks);
    }

    @Override
    public int getItemViewType(int position) {
        BaseTask baseTask = getItem(position);
        if (baseTask.getTaskId().equals("0") || baseTask.getTaskId().equals("1") ||
                baseTask.getTaskId().equals("2") || baseTask.getTaskId().equals("3")) {
            return R.layout.adapter_header;
        }
        return R.layout.adapter_task_dashboard;
    }

    private static DiffUtil.ItemCallback<BaseTask> UserDiffCallback = new DiffUtil.ItemCallback<BaseTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull BaseTask oldItem, @NonNull BaseTask newItem) {
            return oldItem.getTaskId().equals(newItem.getTaskId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BaseTask oldItem, @NonNull BaseTask newItem) {
            return oldItem.equals(newItem);
        }
    };
}
