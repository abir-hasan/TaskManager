package com.example.abirhasan.finaltest.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.google.firebase.database.Exclude;

public class BaseTask implements Parcelable {

    private String taskId;
    private String title;
    private String details;
    private long dueDate;
    private boolean hasAlarm;
    private TaskPriority priority;
    private Status status;
    private transient boolean isSection;

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

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.taskId);
        dest.writeString(this.title);
        dest.writeString(this.details);
        dest.writeLong(this.dueDate);
        dest.writeByte(this.hasAlarm ? (byte) 1 : (byte) 0);
        dest.writeInt(this.priority == null ? -1 : this.priority.ordinal());
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    public BaseTask() {
    }

    protected BaseTask(Parcel in) {
        this.taskId = in.readString();
        this.title = in.readString();
        this.details = in.readString();
        this.dueDate = in.readLong();
        this.hasAlarm = in.readByte() != 0;
        int tmpPriority = in.readInt();
        this.priority = tmpPriority == -1 ? null : TaskPriority.values()[tmpPriority];
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
    }

    public static final Parcelable.Creator<BaseTask> CREATOR = new Parcelable.Creator<BaseTask>() {
        @Override
        public BaseTask createFromParcel(Parcel source) {
            return new BaseTask(source);
        }

        @Override
        public BaseTask[] newArray(int size) {
            return new BaseTask[size];
        }
    };
}
