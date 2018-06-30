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
    private static final String TAG = "DashBoardViewModel";
    private FirebaseQueryLiveData mLiveData;
    private List<BaseTask> mList = new ArrayList<>();
    private List<String> taskDates = new ArrayList<>();
    private MutableLiveData<List<String>> taskDatesLiveData;
    private boolean isFirstImpUrg = true;
    private boolean isFirstNotImpUrg = true;
    private boolean isFirstImpNotUrg = true;
    private boolean isFirstNotImpNotUrg = true;

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
            Log.w(TAG, "apply() called with: dataSnapshot = [" + dataSnapshot + "]");
            mList.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                BaseTask msg = snap.getValue(BaseTask.class);
                if (isFirstImpUrg && msg.getPriority() == TaskPriority.IMP_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstImpUrg = false;
                } else if (isFirstNotImpUrg && msg.getPriority() == TaskPriority.NOT_IMP_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstNotImpUrg = false;
                } else if (isFirstImpNotUrg && msg.getPriority() == TaskPriority.IMP_NOT_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstImpNotUrg = false;
                } else if (isFirstNotImpNotUrg && msg.getPriority() == TaskPriority.NOT_IMP_NOT_URG.getStatus()) {
                    msg.setSection(true);
                    isFirstNotImpNotUrg = false;
                }
                mList.add(msg);
            }
            Log.e(TAG, "apply()  size: " + mList.size());
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
                    Log.i(TAG, "onDataChange: date: " + snap.getKey() + " val: " + snap.getValue());
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
}
