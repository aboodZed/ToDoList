package com.abdallah.todolist.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.abdallah.todolist.R;
import com.abdallah.todolist.models.ToDoList;
import com.abdallah.todolist.models.ToDoTask;

import java.util.concurrent.TimeUnit;

public class TaskDialog extends DialogFragment {

    EditText etTaskName;
    EditText etTaskType;
    EditText etTaskDscription;
    Button btnCancel;
    Button btnCreate;

    private ToDoList toDoList;

    public static TaskDialog newInstance(ToDoList toDoList) {
        TaskDialog fragment = new TaskDialog();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.LIST_EXTRA, toDoList);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_task, container, false);
        toDoList = (ToDoList) getArguments().getSerializable(AppConstants.LIST_EXTRA);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        etTaskName = view.findViewById(R.id.et_task_name);
        etTaskType = view.findViewById(R.id.et_task_type);
        etTaskDscription = view.findViewById(R.id.et_task_dscription);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCreate = view.findViewById(R.id.btn_create);

        btnCancel.setOnClickListener(view1 -> dismiss());
        btnCreate.setOnClickListener(view12 -> validateInput());
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                this.dismiss();
                return true;
            } else return false;
        });
    }

    private void validateInput() {

        String name = etTaskName.getText().toString().trim();
        String type = etTaskType.getText().toString().trim();
        String description = etTaskDscription.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etTaskName.setError(getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(type)) {
            etTaskType.setError(getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(description)) {
            etTaskDscription.setError(getString(R.string.required_field));
            return;
        }

        addTask(name, type, description);
    }

    private void addTask(String name, String type, String dscription) {
        long timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        toDoList.getToDoTasks().add(new ToDoTask(toDoList.getToDoTasks().size(), name, type
                , timeStamp, dscription));

        FirebaseReferences.getListReference(getContext(), toDoList.getId())
                .setValue(toDoList).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dismiss();
            }
        });
    }
}
