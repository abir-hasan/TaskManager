package com.example.abirhasan.finaltest.ui.view_holders;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.AddTaskActivity;
import com.example.abirhasan.finaltest.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvTaskName)
    TextView tvTaskName;
    @BindView(R.id.taskDetails)
    TextView taskDetails;
    @BindView(R.id.tvTaskPriority)
    TextView tvTaskPriority;
    @BindView(R.id.tvTaskStatus)
    TextView tvTaskStatus;
    @BindView(R.id.clView)
    ConstraintLayout clView;
    private BaseTask baseTask;

    public DashBoardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(BaseTask baseTask) {
        this.baseTask = baseTask;
        tvTaskName.setText(baseTask.getTitle());
        taskDetails.setText(baseTask.getDetails());
        String priority = TaskPriority.parseString(baseTask.getPriority());
        String status = Status.parse(baseTask.getStatus()).toString();
        tvTaskPriority.setText(priority);
        tvTaskStatus.setText(status);
    }

    @OnClick(R.id.clView)
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), AddTaskActivity.class);
        intent.putExtra(Constants.KEY_EDIT_TASK, baseTask);
        view.getContext().startActivity(intent);
    }
}
