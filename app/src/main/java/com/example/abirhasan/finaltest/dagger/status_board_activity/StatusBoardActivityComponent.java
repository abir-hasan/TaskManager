package com.example.abirhasan.finaltest.dagger.status_board_activity;

import com.example.abirhasan.finaltest.ui.StatusBoardActivity;

import dagger.Component;

@StatusBoardActivityScope
@Component(modules = StatusBoardActivityModule.class)
public interface StatusBoardActivityComponent {
    void inject(StatusBoardActivity activity);
}
