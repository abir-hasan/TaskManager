package com.example.abirhasan.finaltest.ui.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvTaskName)
    TextView tvTaskName;
    @BindView(R.id.taskDetails)
    TextView taskDetails;
    @BindView(R.id.tvTaskPriority)
    TextView tvTaskPriority;
    @BindView(R.id.tvTaskStatus)
    TextView tvTaskStatus;

    public DashBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(BaseTask baseTask) {
        tvTaskName.setText(baseTask.getTitle());
        taskDetails.setText(baseTask.getDetails());
        String priority = TaskPriority.parse(baseTask.getPriority()).toString();
        String status = Status.parse(baseTask.getStatus()).toString();
        tvTaskPriority.setText(priority);
        tvTaskStatus.setText(status);
    }
}
