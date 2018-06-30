package com.example.abirhasan.finaltest.view_models;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.live_data.FirebaseQueryLiveData;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.utils.StringDateComparator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashBoardViewModel extends ViewModel {
    private FirebaseQueryLiveData mLiveData;
    private List<BaseTask> mList = new ArrayList<>();
    private List<String> taskDates = new ArrayList<>();
    private MutableLiveData<List<String>> taskDatesLiveData;
    private boolean isFirstImpUrg;
    private boolean isFirstNotImpUrg;
    private boolean isFirstImpNotUrg;
    private boolean isFirstNotImpNotUrg;

    @NonNull
    public LiveData<List<BaseTask>> getMessageListLiveData(String user, String date, DatabaseReference dataRef) {
        Query query = dataRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS)
                .child(date).orderByChild(Constants.NODE_PRIORITY);
        mLiveData = new FirebaseQueryLiveData(query);
        return Transformations.map(mLiveData,
                new Deserializer());
    }

    private class Deserializer implements Function<DataSnapshot, List<BaseTask>> {
        @Override
        public List<BaseTask> apply(DataSnapshot dataSnapshot) {
            mList.clear();
            isFirstImpUrg = true;
            isFirstNotImpUrg = true;
            isFirstImpNotUrg = true;
            isFirstNotImpNotUrg = true;
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                BaseTask msg = snap.getValue(BaseTask.class);
                if (isFirstImpUrg && msg.getPriority() == TaskPriority.IMP_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstImpUrg = false;
                    mList.add(getTask(msg.getPriority()));
                } else if (isFirstNotImpUrg && msg.getPriority() == TaskPriority.NOT_IMP_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstNotImpUrg = false;
                    mList.add(getTask(msg.getPriority()));
                } else if (isFirstImpNotUrg && msg.getPriority() == TaskPriority.IMP_NOT_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstImpNotUrg = false;
                    mList.add(getTask(msg.getPriority()));
                } else if (isFirstNotImpNotUrg && msg.getPriority() == TaskPriority.NOT_IMP_NOT_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstNotImpNotUrg = false;
                    mList.add(getTask(msg.getPriority()));
                }
                mList.add(msg);
            }
            return mList;
        }
    }

    public MutableLiveData<List<String>> getUserTaskDates(String user, DatabaseReference dataRef) {
        if (taskDatesLiveData == null) {
            taskDatesLiveData = new MutableLiveData<>();
            loadUserDates(user, dataRef);
        }
        return taskDatesLiveData;
    }

    private void loadUserDates(String user, DatabaseReference dataRef) {
        Query q = dataRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int prevSize = (int) dataSnapshot.getChildrenCount();
                if (prevSize == taskDates.size()) return; // Ignore same date list
                taskDates.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    taskDates.add(snap.getKey());
                }
                Collections.sort(taskDates, new StringDateComparator());
                taskDatesLiveData.postValue(taskDates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BaseTask getTask(int priority) {
        BaseTask task = new BaseTask();
        task.setTitle(TaskPriority.parseString(priority));
        task.setTaskId(String.valueOf(priority));
        return task;
    }
}
