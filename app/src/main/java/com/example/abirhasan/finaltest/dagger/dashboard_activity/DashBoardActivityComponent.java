package com.example.abirhasan.finaltest.dagger.dashboard_activity;

import com.example.abirhasan.finaltest.ui.DashBoardActivity;

import dagger.Component;

@DashBoardActivityScope
@Component(modules = DashBoardActivityModule.class)
public interface DashBoardActivityComponent {
    void inject(DashBoardActivity activity);
}
