package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    TextView tvListName;
    TextView tvListDelete;
    LinearLayout etCreate;
    RecyclerView rvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bindViews();
    }

    private void bindViews() {
        tvListName = findViewById(R.id.tv_list_name);
        tvListDelete = findViewById(R.id.tv_list_delete);
        etCreate = findViewById(R.id.et_create);
        rvTasks = findViewById(R.id.rv_tasks);
    }
}