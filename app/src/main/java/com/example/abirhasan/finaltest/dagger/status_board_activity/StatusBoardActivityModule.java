package com.example.abirhasan.finaltest.dagger.status_board_activity;

import android.arch.lifecycle.ViewModelProviders;

import com.example.abirhasan.finaltest.ui.StatusBoardActivity;
import com.example.abirhasan.finaltest.ui.adapters.StatusBoardAdapter;
import com.example.abirhasan.finaltest.view_models.StatusBoardViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class StatusBoardActivityModule {

    private final StatusBoardActivity activity;

    public StatusBoardActivityModule(StatusBoardActivity activity) {
        this.activity = activity;
    }

    @Provides
    @StatusBoardActivityScope
    public StatusBoardActivity getAddTaskActivity() {
        return this.activity;
    }

    @Provides
    @StatusBoardActivityScope
    public StatusBoardViewModel getViewModel() {
        return ViewModelProviders.of(activity).get(StatusBoardViewModel.class);
    }

    @Provides
    @StatusBoardActivityScope
    public StatusBoardAdapter getAdapter() {
        return new StatusBoardAdapter();
    }

    @Provides
    @StatusBoardActivityScope
    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
