package com.example.abirhasan.finaltest;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abirhasan.finaltest.enums.Status;
import com.example.abirhasan.finaltest.enums.TaskPriority;
import com.example.abirhasan.finaltest.models.BaseTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String taskId = "1";
    private DatabaseReference mDatabase;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userRef = mDatabase.child("users").child("abir").child("tasks");
        mDatabase.child("users").child("abir").child("tasks").child(getUserId()).setValue(getSampleTask(1));
        //mDatabase.child("users").child("abir").child("tasks").child(getUserId()).setValue(getSampleTask(2));

        //Query query = mDatabase.child("users").child("abir");
        Query query = mDatabase.child("users").child("abir").child("tasks").orderByChild("priority");
        //Query query = mDatabase.child("users").child("abir").orderByChild("tasks/priority");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                List<BaseTask> taskList = new ArrayList<>();
                Map<String, BaseTask> sfa = new HashMap<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onChildAdded: "+snap.getKey() +" "+snap.getValue());
                   /* BaseTask msg = snap.getValue(BaseTask.class);
                    Log.d(TAG, "onChildAdded: id: "+msg.getTaskId()+" prio: "+msg.getPriority());
                    taskList.add(msg);*/
                    //Log.w(TAG, "onChildAdded: "+snap.c );
                }
                Log.w(TAG, "onChildAdded: " + taskList.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i(TAG, "onChildChanged() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved() called with: dataSnapshot = [" + dataSnapshot + "]");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
            }
        });
        Log.e(TAG, "onCreate: ");

       /* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: " );
                mDatabase.child("users").child("abir").child("tasks").child(getUserId()).setValue(getSampleTask(4));
            }
        },4000);*/
    }

    private String getUserId() {
        return userRef.push().getKey();
    }


    private BaseTask getSampleTask(int taskId) {
        BaseTask task = new BaseTask();
        //task.setTaskId(taskId);
        task.setTitle("Do the project");
        task.setDetails("Working on it");
        task.setDueDate(112312);
        task.setHasAlarm(false);
        task.setPriority(TaskPriority.IMP_URG.getStatus());
        task.setStatus(Status.TO_DO.getStatus());
        return task;
    }
}
