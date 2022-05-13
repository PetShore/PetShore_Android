package com.petshore.petshore_android.network.storage;

import android.content.SharedPreferences;

public class sharePreferencesStorage implements Storage{
    SharedPreferences sharedPreferences;
    private final String tokenKey = "TokenKey";

    public sharePreferencesStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveToken(String token) {
        sharedPreferences.edit().putString(tokenKey, token ).apply();
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(tokenKey, "");
    }
}
