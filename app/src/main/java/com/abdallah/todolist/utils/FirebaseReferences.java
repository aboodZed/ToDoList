package com.abdallah.todolist.utils;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseReferences {

    public static DatabaseReference getUserListsReference(Context context) {
        return FirebaseDatabase.getInstance().getReference()
                .child(AppConstants.TABLE_TODOLIST_NAME)
                .child(new SharedPreference(context).getUserId())
                .child(AppConstants.TABLE_LISTS_NAME);
    }


    public static DatabaseReference getListReference(Context context, String id) {
        return getUserListsReference(context).child(id);
    }

    public static DatabaseReference getTaskReference(Context context, String list_id, int id) {
        return getListReference(context, list_id).child(AppConstants.TABLE_TASKS_NAME).child(id + "");
    }
}
