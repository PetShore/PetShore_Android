package com.petshore.petshore_android.network.service;

import com.petshore.petshore_android.network.model.TokenUser;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthServices {
    @POST("/login")
    TokenUser login(@Body TokenUser tokenUser);
}
