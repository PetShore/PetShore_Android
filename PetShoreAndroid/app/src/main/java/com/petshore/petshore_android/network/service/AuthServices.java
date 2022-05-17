package com.petshore.petshore_android.network.service;

import com.petshore.petshore_android.network.model.TokenUser;
import com.petshore.petshore_android.network.model.Usuario;

import java.net.HttpURLConnection;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthServices {
    @POST("/auth/login")
    Observable<TokenUser> login(@Body TokenUser tokenUser);
    @POST("/auth/addUser")
    Observable<HttpURLConnection> addUser(@Body Usuario usuario);
}
