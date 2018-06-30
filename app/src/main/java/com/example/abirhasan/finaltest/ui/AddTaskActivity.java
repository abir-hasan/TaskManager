package com.example.abirhasan.finaltest.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.utils.AppUtils;
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
    private long dueDate = 0L;
    private String dateValue;

    @BindView(R.id.etTaskName)
    EditText etTaskName;
    @BindView(R.id.etTaskDetails)
    EditText etTaskDetails;
    @BindView(R.id.tvPickDate)
    TextView tvPickDate;
    @BindView(R.id.spPriority)
    Spinner spPriority;
    @BindView(R.id.btnAddTask)
    Button btnAddTask;
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
        setPriorities();
        observeTaskAdditionStatus();
    }

    private void setPriorities() {
        String[] priorityList = getResources().getStringArray(R.array.priorities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, priorityList);
        spPriority.setAdapter(adapter);
        spPriority.setSelection(priorityList.length - 1);
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

    @OnClick(R.id.tvPickDate)
    public void onPickDateClick() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy",
                                Locale.getDefault());
                        dateValue = format.format(calendar.getTime().getTime());
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
}
