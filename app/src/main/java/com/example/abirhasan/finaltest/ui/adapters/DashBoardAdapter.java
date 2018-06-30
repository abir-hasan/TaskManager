package com.example.abirhasan.finaltest.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.view_holders.DashBoardViewHolder;

import java.util.List;

public class DashBoardAdapter extends ListAdapter<BaseTask, DashBoardViewHolder> {

    private static final String TAG = "DashBoardAdapter";

    public DashBoardAdapter() {
        super(UserDiffCallback);
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task_dashboard,
                parent, false);
        return new DashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        BaseTask baseTask = getItem(position);
        holder.bind(baseTask);
    }

    public void setData(List<BaseTask> tasks) {
        submitList(tasks);
    }

    private static DiffUtil.ItemCallback<BaseTask> UserDiffCallback = new DiffUtil.ItemCallback<BaseTask>() {
        @Override
        public boolean areItemsTheSame(@NonNull BaseTask oldItem, @NonNull BaseTask newItem) {
            Log.i(TAG, "areItemsTheSame() called with: oldItem = [" + oldItem.getTaskId()
                    + "], newItem = [" + newItem.getTaskId() + "] res: "
                    + oldItem.getTaskId().equals(newItem.getTaskId())
                    + " same? " + oldItem.equals(newItem));
            return oldItem.getTaskId().equals(newItem.getTaskId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BaseTask oldItem, @NonNull BaseTask newItem) {
            Log.w(TAG, "areContentsTheSame() called with: oldItem = [" + oldItem
                    + "], newItem = [" + newItem + "]");
            return oldItem.equals(newItem);
        }
    };
}
