package com.example.abirhasan.finaltest.dagger.rest;

import com.example.abirhasan.finaltest.network.EndPoints;
import com.squareup.picasso.Picasso;

import dagger.Component;


@EndPointScope
@Component(modules = {EndPointModule.class, PicassoModule.class})
public interface EndPointComponent {

    Picasso getPicasso();

    EndPoints getTMDbService();
}
