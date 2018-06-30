package com.example.abirhasan.finaltest.models;

import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.google.firebase.database.Exclude;

public class BaseTask {

    private String taskId;
    private String title;
    private String details;
    private long dueDate;
    private boolean hasAlarm;
    private TaskPriority priority;
    private Status status;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    @Exclude
    public TaskPriority getPriorityVal() {
        return priority;
    }

    public int getPriority() {
        return priority.getStatus();

    }

    public void setPriority(int priority) {
        this.priority = TaskPriority.parse(priority);
    }

    @Exclude
    public Status getStatusVal() {
        return status;
    }

    public int getStatus() {
        return status.getStatus();
    }

    public void setStatus(int status) {
        this.status = Status.parse(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseTask task = (BaseTask) o;

        if (dueDate != task.dueDate) return false;
        if (hasAlarm != task.hasAlarm) return false;
        if (taskId != null ? !taskId.equals(task.taskId) : task.taskId != null) return false;
        if (title != null ? !title.equals(task.title) : task.title != null) return false;
        if (details != null ? !details.equals(task.details) : task.details != null) return false;
        if (priority != task.priority) return false;
        return status == task.status;
    }
}
