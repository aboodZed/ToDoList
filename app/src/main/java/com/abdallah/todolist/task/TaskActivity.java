package com.abdallah.todolist.task;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abdallah.todolist.R;
import com.abdallah.todolist.models.ToDoList;
import com.abdallah.todolist.models.ToDoTask;
import com.abdallah.todolist.utils.AppConstants;
import com.abdallah.todolist.utils.FirebaseReferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvEdit;

    TextView tvTaskName;
    TextView tvTaskType;
    TextView tvDate;
    LinearLayout llEdit;
    EditText etEditName;
    EditText etEditType;
    View view;
    TextView description;
    TextView tvDescription;
    EditText etDescription;
    TextView tvDelete;

    private boolean is_edit;
    private ToDoTask toDoTask;
    private ToDoList toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        bindViews();

        toDoTask = (ToDoTask) getIntent().getSerializableExtra(AppConstants.TASK_EXTRA);
        toDoList = (ToDoList) getIntent().getSerializableExtra(AppConstants.LIST_EXTRA);
        setData();
    }

    private void setData() {
        tvTaskName.setText(toDoTask.getName());
        tvTaskType.setText(toDoTask.getType());
        tvDate.setText(getDate(toDoTask.getDate()) + " - " + getTime(toDoTask.getDate()));
        tvDescription.setText(toDoTask.getDetails());


        etEditName.setText(toDoTask.getName());
        etEditType.setText(toDoTask.getType());
        etDescription.setText(toDoTask.getDetails());
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        tvEdit = findViewById(R.id.tv_edit);

        tvTaskName = findViewById(R.id.tv_task_name);
        tvTaskType = findViewById(R.id.tv_task_type);
        tvDate = findViewById(R.id.tv_date);
        llEdit = findViewById(R.id.ll_edit);
        etEditName = findViewById(R.id.et_edit_name);
        etEditType = findViewById(R.id.et_edit_type);
        view = findViewById(R.id.view);
        description = findViewById(R.id.description);
        tvDescription = findViewById(R.id.tv_description);
        etDescription = findViewById(R.id.et_description);
        tvDelete = findViewById(R.id.tv_delete);

        ivBack.setOnClickListener(view -> onBackPressed());
        tvEdit.setOnClickListener(view -> {
            if (!is_edit) {
                tvTaskName.setVisibility(View.GONE);
                tvTaskType.setVisibility(View.GONE);
                tvDate.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);

                llEdit.setVisibility(View.VISIBLE);
                etDescription.setVisibility(View.VISIBLE);
                tvEdit.setText(getString(R.string.save));

                is_edit = true;
            } else {
                tvTaskName.setVisibility(View.VISIBLE);
                tvTaskType.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                tvDescription.setVisibility(View.VISIBLE);

                llEdit.setVisibility(View.GONE);
                etDescription.setVisibility(View.GONE);
                tvEdit.setText(getString(R.string.edit));
                is_edit = false;

                String name = etEditName.getText().toString().trim();
                String type = etEditType.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    etEditName.setError(getString(R.string.required_field));
                    return;
                }
                if (TextUtils.isEmpty(type)) {
                    etEditType.setError(getString(R.string.required_field));
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    etDescription.setError(getString(R.string.required_field));
                    return;
                }

                ToDoTask t = toDoTask;
                t.setName(name);
                t.setType(type);
                t.setDetails(description);

                FirebaseReferences.getTaskReference(this, toDoList.getId(), toDoTask.getId())
                        .setValue(t).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                toDoTask = t;
                                setData();
                            }
                        });
            }
        });

        tvDelete.setOnClickListener(view ->
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.delete_Task))
                        .setPositiveButton(R.string.yes, ((dialogInterface, j) -> {
                            for (int i = 0; i < toDoList.getToDoTasks().size(); i++) {
                                if (toDoList.getToDoTasks().get(i).getId() == toDoTask.getId()) {
                                    toDoList.getToDoTasks().remove(toDoList.getToDoTasks().get(i));
                                    break;
                                }
                            }
                            for (int i = 0; i < toDoList.getToDoTasks().size(); i++) {
                                toDoList.getToDoTasks().get(i).setId(i);
                            }

                            FirebaseReferences.getListReference(TaskActivity.this, toDoList.getId())
                                    .setValue(toDoList).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    onBackPressed();
                                } else {
                                    Toast.makeText(TaskActivity.this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }))
                        .setNegativeButton(R.string.no, null)
                        .show());
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

    public static String getTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm:ss", cal).toString();
        return date;
    }
}