package com.example.abirhasan.finaltest.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DaggerDashBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DashBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DashBoardActivityModule;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.adapters.DashBoardAdapter;
import com.example.abirhasan.finaltest.utils.AppUtils;
import com.example.abirhasan.finaltest.view_models.DashBoardViewModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardActivity extends AppCompatActivity {
    private static final String TAG = "DashBoardActivity";
    @BindView(R.id.fabAddTask)
    FloatingActionButton fabAddTask;
    @BindView(R.id.rvDashboard)
    RecyclerView rvDashboard;
    @Inject
    DatabaseReference reference;
    @Inject
    DashBoardAdapter adapter;
    @Inject
    DashBoardViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initDependency();
        ButterKnife.bind(this);
        setUI();
        observeData();
    }

    private void setUI() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvDashboard.setLayoutManager(manager);
        rvDashboard.setAdapter(adapter);
    }

    private void initDependency() {
        DashBoardActivityComponent activityComponent = DaggerDashBoardActivityComponent.builder()
                .dashBoardActivityModule(new DashBoardActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    private void observeData() {
        String user = AppUtils.getUser(this);
        viewModel.getMessageListLiveData(user, reference).observe(this, new Observer<List<BaseTask>>() {
            @Override
            public void onChanged(@Nullable List<BaseTask> baseTasks) {
                Log.d(TAG, "onChanged() called with: baseTasks = [" + baseTasks.size() + "]");
                List<BaseTask> tasks = new ArrayList<>(baseTasks);
                adapter.setData(tasks);
            }
        });
    }


    @OnClick(R.id.fabAddTask)
    public void onAddTaskClick() {
        startActivity(new Intent(DashBoardActivity.this, AddTaskActivity.class));
    }
}
