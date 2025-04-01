package com.example.syy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "user_session";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_ROLE = "userRole";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn, String role) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.putString(USER_ROLE, role);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public String getUserRole() {
        return sharedPreferences.getString(USER_ROLE, null);
    }
}

