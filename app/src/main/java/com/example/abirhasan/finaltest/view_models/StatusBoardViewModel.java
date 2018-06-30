package com.example.abirhasan.finaltest.view_models;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.live_data.FirebaseQueryLiveData;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.example.abirhasan.finaltest.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class StatusBoardViewModel extends ViewModel {
    private List<BaseTask> mList = new ArrayList<>();
    private Status status;

    @NonNull
    public LiveData<List<BaseTask>> getTaskListLiveData(String user, String date,
                                                        DatabaseReference dataRef, Status status) {
        this.status = status;
        Query query = dataRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS)
                .child(date).orderByChild(Constants.NODE_STATUS).equalTo(status.getStatus());
        //For all data
        //Query query = dataRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS);
        FirebaseQueryLiveData mLiveData = new FirebaseQueryLiveData(query);
        return Transformations.map(mLiveData,
                new Deserializer());
    }

    private class Deserializer implements Function<DataSnapshot, List<BaseTask>> {
        @Override
        public List<BaseTask> apply(DataSnapshot dataSnapshot) {
            mList.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                BaseTask msg = snap.getValue(BaseTask.class);
                mList.add(msg);

            }
            // Parse all data
             /*for (DataSnapshot snap : dataSnapshot.getChildren()) {
                for (DataSnapshot inn : snap.getChildren()) {
                    BaseTask msg = inn.getValue(BaseTask.class);
                    if (msg.getStatus() == status.getStatus())
                        mList.add(msg);
                }
            }*/
            return mList;
        }
    }
}
