package com.example.abirhasan.finaltest.ui.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusBoardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvTaskName)
    TextView tvTaskName;
    @BindView(R.id.tvTaskDetails)
    TextView tvTaskDetails;
    @BindView(R.id.tvTaskPriority)
    TextView tvTaskPriority;
    @BindView(R.id.tvTaskDate)
    TextView tvTaskDate;

    public StatusBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(BaseTask baseTask) {
        String priority = TaskPriority.parseString(baseTask.getPriority());
        String date = AppUtils.getFormattedDate(baseTask.getDueDate());
        tvTaskName.setText(baseTask.getTitle());
        tvTaskDetails.setText(baseTask.getDetails());
        tvTaskPriority.setText(priority);
        tvTaskDate.setText(date);
    }
}
