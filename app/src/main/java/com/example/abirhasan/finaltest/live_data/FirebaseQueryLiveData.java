package com.example.abirhasan.finaltest.live_data;

import android.arch.lifecycle.LiveData;
import android.os.Handler;
import android.util.Log;

import com.example.abirhasan.finaltest.models.BaseTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String TAG = "FirebaseQueryLiveData";
    private final Query query;
    private final ValueEventListener valueListener = new mValueEventListener();
    //private final ChildEventListener childListener = new MyEventListener();

    private List<BaseTask> mQueryValuesList = new ArrayList<>();

    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            query.removeEventListener(valueListener);
            listenerRemovePending = false;
        }
    };

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference dbReference) {
        this.query = dbReference;
    }

    @Override
    protected void onActive() {
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        } else {
            query.orderByChild("priority").addValueEventListener(valueListener);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }


    private class mValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "onDataChange: ");
            setValue(dataSnapshot);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "Cannot listen to query " + query, databaseError.toException());
        }
    }


    /*private class MyEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot != null) {
                Log.d(LOG_TAG, "onChildAdded(): previous child name = " + s);
                setValue(dataSnapshot);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    BaseTask task = snap.getValue(BaseTask.class);
                    mQueryValuesList.add(msg);
                }
            } else {
                Log.w(LOG_TAG, "onChildAdded(): data snapshot is NULL");
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Cannot listen to query " + query, databaseError.toException());
        }

    }*/

}
