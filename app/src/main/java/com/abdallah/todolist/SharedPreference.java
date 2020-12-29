package com.abdallah.todolist;

import android.content.Context;
import android.content.SharedPreferences;

class SharedPreference {

    public final static String KEY_USER_ID = "user_id";

    private static final String PREF_NAME = "AppLocal";
    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Context context;

    public SharedPreference(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserId(String s) {
        editor.putString(KEY_USER_ID, s);
        editor.apply();
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, "");
    }

}
