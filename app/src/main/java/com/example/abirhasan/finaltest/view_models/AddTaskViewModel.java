package com.example.abirhasan.finaltest.view_models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class AddTaskViewModel extends ViewModel {
    private MutableLiveData<Boolean> isTaskAdded;
    private DatabaseReference userRef;

    public MutableLiveData<Boolean> getTaskAdditionStatus() {
        if (isTaskAdded == null) {
            isTaskAdded = new MutableLiveData<>();
        }
        return isTaskAdded;
    }

    public void addTask(DatabaseReference dbRef, String user, String taskName, String taskDetails,
                        String dateValue, long dueDate, boolean hasAlarms, TaskPriority priority) {
        userRef = dbRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS);
        String key = getTaskKey();
        BaseTask task = getSampleTask(key, taskName, taskDetails, dueDate, hasAlarms, priority);
        dbRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS)
                .child(key).setValue(task, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError,
                                   @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    isTaskAdded.postValue(true);
                } else {
                    isTaskAdded.postValue(false);
                }
            }
        });
    }

    private String getTaskKey() {
        return userRef.push().getKey();
    }

    private BaseTask getSampleTask(String taskId, String taskName, String taskDetails, long dueDate,
                                   boolean hasAlarms, TaskPriority priority) {
        BaseTask task = new BaseTask();
        task.setTaskId(taskId);
        task.setTitle(taskName);
        task.setDetails(taskDetails);
        task.setDueDate(dueDate);
        task.setHasAlarm(hasAlarms);
        task.setPriority(priority.getStatus());
        task.setStatus(Status.TO_DO.getStatus());
        return task;
    }
}
