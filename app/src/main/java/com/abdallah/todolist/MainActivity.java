package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView ivBack;
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
        ivBack =  findViewById(R.id.iv_back);
        etSearch = findViewById(R.id.et_search);
        etCreate = findViewById(R.id.et_create);
        rvLists = findViewById(R.id.rv_lists);

        ivBack.setOnClickListener(view -> onBackPressed());

        ArrayList<ToDoList> toDoLists = new ArrayList<>();
        toDoLists.add(new ToDoList(1, "Home", 2));
        toDoLists.add(new ToDoList(2, "Personal", 1));
        toDoLists.add(new ToDoList(3, "Work", 3));
        toDoLists.add(new ToDoList(4, "toDay", 2));

        ListsAdapter listsAdapter = new ListsAdapter(toDoLists, this);
        rvLists.setLayoutManager(new LinearLayoutManager(this));
        rvLists.setItemAnimator(new DefaultItemAnimator());
        rvLists.setAdapter(listsAdapter);
    }
}