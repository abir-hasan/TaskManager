package com.example.abirhasan.finaltest.view_models;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.live_data.FirebaseQueryLiveData;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class DashBoardViewModel extends ViewModel {
    private static final String TAG = "DashBoardViewModel";
    private FirebaseQueryLiveData mLiveData;
    private List<BaseTask> mList = new ArrayList<>();

    @NonNull
    public LiveData<List<BaseTask>> getMessageListLiveData(String user, DatabaseReference dataRef) {
        dataRef = dataRef.child(Constants.NODE_USERS).child(user).child(Constants.NODE_TASKS);
        mLiveData = new FirebaseQueryLiveData(dataRef);
        LiveData<List<BaseTask>> mMessageLiveData = Transformations.map(mLiveData,
                new Deserializer());
        return mMessageLiveData;
    }

    private class Deserializer implements Function<DataSnapshot, List<BaseTask>> {

        @Override
        public List<BaseTask> apply(DataSnapshot dataSnapshot) {
            Log.w(TAG, "apply() called with: dataSnapshot = [" + dataSnapshot + "]");
            mList.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                BaseTask msg = snap.getValue(BaseTask.class);
                mList.add(msg);
            }
            Log.e(TAG, "apply()  size: " + mList.size());
            return mList;
        }
    }
}
