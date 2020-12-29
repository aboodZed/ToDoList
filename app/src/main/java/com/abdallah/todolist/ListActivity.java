package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ImageView ivBack;
    ImageView ivSearch;
    TextView tvListName;
    TextView tvListDelete;
    LinearLayout etCreate;
    RecyclerView rvTasks;

    private ToDoList toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bindViews();
        toDoList = (ToDoList) getIntent().getSerializableExtra(AppConstants.LIST_EXTRA);
    }

    private void bindViews() {
        ivBack =  findViewById(R.id.iv_back);
        ivSearch =  findViewById(R.id.iv_search);
        tvListName = findViewById(R.id.tv_list_name);
        tvListDelete = findViewById(R.id.tv_list_delete);
        etCreate = findViewById(R.id.et_create);
        rvTasks = findViewById(R.id.rv_tasks);

        ivBack.setOnClickListener(view -> onBackPressed());
        ivSearch.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this,SearchActivity.class);
            startActivity(intent);
        });

        ArrayList<ToDoTask> toDoTasks = new ArrayList<>();
        toDoTasks.add(new ToDoTask(1, "homework", "duty", 122331133, "finish all my homework in time before 10 PM"));
        toDoTasks.add(new ToDoTask(2, "Ali", "meet", 1232435454, "meet ali in a cafe in 11 PM"));

        TasksAdapter tasksAdapter = new TasksAdapter(toDoTasks, this);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(tasksAdapter);
    }
}