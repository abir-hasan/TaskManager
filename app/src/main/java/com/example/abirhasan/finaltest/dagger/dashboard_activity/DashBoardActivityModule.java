package com.example.abirhasan.finaltest.dagger.dashboard_activity;

import android.arch.lifecycle.ViewModelProviders;

import com.example.abirhasan.finaltest.ui.DashBoardActivity;
import com.example.abirhasan.finaltest.ui.adapters.DashBoardAdapter;
import com.example.abirhasan.finaltest.view_models.DashBoardViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class DashBoardActivityModule {

    private final DashBoardActivity activity;

    public DashBoardActivityModule(DashBoardActivity activity) {
        this.activity = activity;
    }

    @Provides
    @DashBoardActivityScope
    public DashBoardActivity getAddTaskActivity() {
        return this.activity;
    }

    @Provides
    @DashBoardActivityScope
    public DashBoardViewModel getViewModel() {
        return ViewModelProviders.of(activity).get(DashBoardViewModel.class);
    }


    @Provides
    @DashBoardActivityScope
    public DashBoardAdapter getAdapter() {
        return new DashBoardAdapter();
    }

    @Provides
    @DashBoardActivityScope
    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
