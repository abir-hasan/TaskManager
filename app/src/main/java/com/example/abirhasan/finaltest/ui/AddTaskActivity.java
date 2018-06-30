package com.example.abirhasan.finaltest.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.dagger.add_task.AddTaskActivityComponent;
import com.example.abirhasan.finaltest.dagger.add_task.AddTaskActivityModule;
import com.example.abirhasan.finaltest.dagger.add_task.DaggerAddTaskActivityComponent;
import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.utils.AppUtils;
import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.view_models.AddTaskViewModel;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class AddTaskActivity extends AppCompatActivity {
    private TaskPriority priority;
    private Status taskStatus;
    private long dueDate = 0L;
    private String dateValue;
    private BaseTask baseTask;

    @BindView(R.id.etTaskName)
    EditText etTaskName;
    @BindView(R.id.etTaskDetails)
    EditText etTaskDetails;
    @BindView(R.id.tvPickDate)
    TextView tvPickDate;
    @BindView(R.id.spPriority)
    Spinner spPriority;
    @BindView(R.id.spStatus)
    Spinner spStatus;
    @BindView(R.id.btnAddTask)
    Button btnAddTask;
    @BindView(R.id.btnEditTask)
    Button btnEditTask;
    @Inject
    AddTaskViewModel viewModel;
    @Inject
    DatabaseReference dbRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initDependency();
        ButterKnife.bind(this);
        checkIntentValue();
        observeTaskAdditionStatus();
    }

    private void setPriorities(int pos) {
        String[] priorityList = getResources().getStringArray(R.array.priorities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, priorityList);
        spPriority.setAdapter(adapter);
        spPriority.setSelection(pos);
    }

    private void initDependency() {
        AddTaskActivityComponent activityComponent = DaggerAddTaskActivityComponent.builder()
                .addTaskActivityModule(new AddTaskActivityModule(this))
                .build();
        activityComponent.inject(this);
    }

    private void observeTaskAdditionStatus() {
        viewModel.getTaskAdditionStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean taskAddedSuccessFully) {
                if (taskAddedSuccessFully != null && taskAddedSuccessFully) {
                    finish();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Retry",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void checkIntentValue() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.KEY_EDIT_TASK)) {
            baseTask = intent.getParcelableExtra(Constants.KEY_EDIT_TASK);
            setTitle(R.string.edit_task);
            tvPickDate.setVisibility(View.GONE);
            btnAddTask.setVisibility(View.GONE);
            spStatus.setVisibility(View.VISIBLE);
            btnEditTask.setVisibility(View.VISIBLE);
            etTaskName.setText(baseTask.getTitle());
            etTaskDetails.setText(baseTask.getDetails());
            setTaskStatuses(baseTask.getStatus());
            setPriorities(baseTask.getPriority());
        } else {
            setTitle(R.string.add_task);
            setPriorities(0);
        }
    }

    private void setTaskStatuses(int pos) {
        String[] statusList = getResources().getStringArray(R.array.task_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, statusList);
        spStatus.setAdapter(adapter);
        spStatus.setSelection(pos);
    }

    @OnClick(R.id.btnAddTask)
    public void onAddTaskClick() {
        String taskName = etTaskName.getText().toString();
        String taskDetails = etTaskDetails.getText().toString();
        if (taskName.isEmpty() || taskDetails.isEmpty() || priority == null || dueDate == 0L) {
            Toast.makeText(this, "Put all data", Toast.LENGTH_SHORT).show();
        } else {
            String user = AppUtils.getUser(AddTaskActivity.this);
            viewModel.addTask(dbRef, user, taskName, taskDetails, dateValue, dueDate,
                    false, priority);
        }
    }

    @OnClick(R.id.btnEditTask)
    public void onEditTask() {
        String taskName = etTaskName.getText().toString();
        String taskDetails = etTaskDetails.getText().toString();
        if (taskName.isEmpty() || taskDetails.isEmpty() || taskStatus == null || priority == null) {
            Toast.makeText(this, "Put all data", Toast.LENGTH_SHORT).show();
        } else {
            baseTask.setTitle(taskName);
            baseTask.setDetails(taskDetails);
            baseTask.setPriority(priority.getStatus());
            baseTask.setStatus(taskStatus.getStatus());
            String user = AppUtils.getUser(AddTaskActivity.this);
            viewModel.editData(dbRef, user, baseTask);
        }
    }

    @OnClick(R.id.tvPickDate)
    public void onPickDateClick() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        dateValue = AppUtils.getFormat().format(calendar.getTime().getTime());
                        tvPickDate.setText(dateValue);
                        dueDate = calendar.getTime().getTime();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    @OnItemSelected(R.id.spPriority)
    public void getItemClick(int pos) {
        priority = TaskPriority.parse(pos);
    }

    @OnItemSelected(R.id.spStatus)
    public void getItemClickStatus(int pos) {
        taskStatus = Status.parse(pos);
    }
}
