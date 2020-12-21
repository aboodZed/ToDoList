package com.abdallah.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvTaskName;
    TextView tvTaskType;
    TextView tvDate;
    TextView tvDescription;
    TextView tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        bindViews();

        setData((ToDoTask) Objects.requireNonNull(getIntent().getSerializableExtra("task")));
    }

    private void setData(ToDoTask task) {
        tvTaskName.setText(task.getName());
        tvTaskType.setText(task.getType());
        tvDate.setText(getDate(task.getDate()));
        tvDescription.setText(task.getDetails());
    }

    private void bindViews() {
        ivBack =  findViewById(R.id.iv_back);
        tvTaskName = findViewById(R.id.tv_task_name);
        tvTaskType = findViewById(R.id.tv_task_type);
        tvDate = findViewById(R.id.tv_date);
        tvDescription = findViewById(R.id.tv_description);
        tvDelete = findViewById(R.id.tv_delete);

        ivBack.setOnClickListener(view -> onBackPressed());

    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}