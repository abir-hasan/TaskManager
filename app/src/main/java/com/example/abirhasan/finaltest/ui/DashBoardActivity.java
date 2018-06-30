package com.example.abirhasan.finaltest.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DaggerDashBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DashBoardActivityComponent;
import com.example.abirhasan.finaltest.dagger.dashboard_activity.DashBoardActivityModule;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.ui.adapters.DashBoardAdapter;
import com.example.abirhasan.finaltest.utils.AppUtils;
import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.view_models.DashBoardViewModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class DashBoardActivity extends AppCompatActivity {
    private static final String TAG = "DashBoardActivity";
    private String selectedDate;
    private String user;
    @BindView(R.id.fabAddTask)
    FloatingActionButton fabAddTask;
    @BindView(R.id.rvDashboard)
    RecyclerView rvDashboard;
    @BindView(R.id.spTaskDates)
    Spinner spTaskDates;
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
        setTitle("Task Board");
        initDependency();
        ButterKnife.bind(this);
        user = AppUtils.getUser(this);
        setUI();
        getUserSpecificTaskDates();
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

    private void getUserSpecificTaskDates() {
        viewModel.getUserTaskDates(user, reference).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null) {
                    spTaskDates.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DashBoardActivity.this,
                            R.layout.support_simple_spinner_dropdown_item, strings);
                    spTaskDates.setAdapter(adapter);
                    //spTaskDates.setSelection(strings.size() - 1);
                }
            }
        });
    }

    private void observeData(String taskDate) {
        viewModel.getMessageListLiveData(user, taskDate, reference).observe(this, new Observer<List<BaseTask>>() {
            @Override
            public void onChanged(@Nullable List<BaseTask> baseTasks) {
                Log.d(TAG, "onChanged() called with: baseTasks = [" + baseTasks.size() + "]");
                List<BaseTask> tasks = new ArrayList<>(baseTasks);
                adapter.setData(tasks);
            }
        });
    }

    @OnItemSelected(R.id.spTaskDates)
    public void onItemSelect(AdapterView<?> parent, View view, int position, long id) {
        selectedDate = String.valueOf(parent.getAdapter().getItem(position));
        observeData(selectedDate);
        Log.d(TAG, "onItemSelect: " + selectedDate);
    }

    @OnClick(R.id.fabAddTask)
    public void onAddTaskClick() {
        startActivity(new Intent(DashBoardActivity.this, AddTaskActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.status_board) {
            goToStatusBoard();
        } else {
            doLogOut();
        }
        return true;
    }

    private void doLogOut() {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME,
                MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToStatusBoard() {
        if (selectedDate == null || selectedDate.isEmpty()) return;
        Intent intent = new Intent(this, StatusBoardActivity.class);
        intent.putExtra(Constants.KEY_TASK_DATE, selectedDate);
        startActivity(intent);
    }
}
