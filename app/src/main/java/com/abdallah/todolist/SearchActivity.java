package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ImageView ivBack;
    EditText etSearch;
    RecyclerView rvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindViews();
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        etSearch = findViewById(R.id.et_search);
        rvResult = findViewById(R.id.rv_result);

        ivBack.setOnClickListener(view -> onBackPressed());

        ArrayList<ToDoTask> toDoTasks = new ArrayList<>();
        toDoTasks.add(new ToDoTask(1, "homework", "duty", 122331133, "finish all my homework in time before 10 PM"));
        toDoTasks.add(new ToDoTask(2, "Ali", "meet", 1232435454, "meet ali in a cafe in 11 PM"));

        SearchAdapter searchAdapter = new SearchAdapter(toDoTasks, this);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.setAdapter(searchAdapter);
    }
}