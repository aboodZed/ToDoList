package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    LinearLayout etCreate;
    RecyclerView rvLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        etSearch = findViewById(R.id.et_search);
        etCreate = findViewById(R.id.et_create);
        rvLists = findViewById(R.id.rv_lists);
    }
}