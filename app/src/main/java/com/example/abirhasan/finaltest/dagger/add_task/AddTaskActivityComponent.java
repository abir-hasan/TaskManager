package com.example.abirhasan.finaltest.dagger.add_task;

import com.example.abirhasan.finaltest.ui.AddTaskActivity;

import dagger.Component;

@AddTaskActivityScope
@Component(modules = AddTaskActivityModule.class)
public interface AddTaskActivityComponent {

    void inject(AddTaskActivity activity);

}
