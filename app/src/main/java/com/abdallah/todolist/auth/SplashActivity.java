package com.abdallah.todolist.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdallah.todolist.R;
import com.abdallah.todolist.main.MainActivity;
import com.abdallah.todolist.utils.SharedPreference;

public class SplashActivity extends AppCompatActivity {

    TextView tvNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindViews();
    }


    private void bindViews() {
        tvNext = findViewById(R.id.tv_next);
        tvNext.setOnClickListener(view -> {
          String id = new SharedPreference(this).getUserId();
            if (TextUtils.isEmpty(id)){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        });
    }
}