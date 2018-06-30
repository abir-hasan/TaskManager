package com.example.abirhasan.finaltest.dagger.add_task;

import android.arch.lifecycle.ViewModelProviders;

import com.example.abirhasan.finaltest.ui.AddTaskActivity;
import com.example.abirhasan.finaltest.view_models.AddTaskViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class AddTaskActivityModule {

    private final AddTaskActivity activity;

    public AddTaskActivityModule(AddTaskActivity activity) {
        this.activity = activity;
    }

    @Provides
    @AddTaskActivityScope
    public AddTaskActivity getAddTaskActivity() {
        return this.activity;
    }

    @Provides
    @AddTaskActivityScope
    public AddTaskViewModel getViewModel() {
        return ViewModelProviders.of(activity).get(AddTaskViewModel.class);
    }


    @Provides
    @AddTaskActivityScope
    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

}
