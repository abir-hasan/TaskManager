package com.example.abirhasan.finaltest.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.dagger.status_board_activity.DaggerStatusBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.status_board_activity.StatusBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.status_board_activity.StatusBoardActivityModule;
import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.adapters.StatusBoardAdapter;
import com.example.abirhasan.finaltest.utils.AppUtils;
import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.view_models.StatusBoardViewModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class StatusBoardActivity extends AppCompatActivity {
    private Status taskStatus;
    private String taskDate;
    private String user;
    @BindView(R.id.rvDashboard)
    RecyclerView rvDashboard;
    @BindView(R.id.spTaskStatus)
    Spinner spTaskStatus;
    @Inject
    DatabaseReference reference;
    @Inject
    StatusBoardAdapter adapter;
    @Inject
    StatusBoardViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_board);
        ButterKnife.bind(this);
        setTitle(R.string.status_board);
        initDependency();
        ButterKnife.bind(this);
        user = AppUtils.getUser(this);
        parseExtraData();
        setUI();
        setTaskStatuses(1);
    }

    private void parseExtraData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.KEY_TASK_DATE)) {
            taskDate = intent.getStringExtra(Constants.KEY_TASK_DATE);
        }
    }

    private void setUI() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvDashboard.setLayoutManager(manager);
        rvDashboard.setAdapter(adapter);
    }

    private void initDependency() {
        StatusBoardActivityComponent activityComponent = DaggerStatusBoardActivityComponent.builder()
                .statusBoardActivityModule(new StatusBoardActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    private void setTaskStatuses(int pos) {
        String[] statusList = getResources().getStringArray(R.array.task_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, statusList);
        spTaskStatus.setAdapter(adapter);
        spTaskStatus.setSelection(pos);
    }

    private void getUserTasks() {
        viewModel.getTaskListLiveData(user, taskDate, reference, taskStatus).observe(this,
                new Observer<List<BaseTask>>() {
                    @Override
                    public void onChanged(@Nullable List<BaseTask> baseTasks) {
                        List<BaseTask> tasks = new ArrayList<>(baseTasks);
                        adapter.setData(tasks);
                    }
                });
    }

    @OnItemSelected(R.id.spTaskStatus)
    public void getItemClickStatus(int pos) {
        taskStatus = Status.parse(pos);
        getUserTasks();
    }
}
