package com.example.abirhasan.finaltest.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.view_holders.DashBoardViewHolder;
import com.example.abirhasan.finaltest.ui.view_holders.StatusBoardViewHolder;

import java.util.List;

public class StatusBoardAdapter extends ListAdapter<BaseTask, StatusBoardViewHolder> {

    public StatusBoardAdapter() {
        super(UserDiffCallback);
    }

    @NonNull
    @Override
    public StatusBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status_board,
                parent, false);
        return new StatusBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusBoardViewHolder holder, int position) {
        BaseTask baseTask = getItem(position);
        holder.bind(baseTask);
    }

    public void setData(List<BaseTask> tasks) {
        submitList(tasks);
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
