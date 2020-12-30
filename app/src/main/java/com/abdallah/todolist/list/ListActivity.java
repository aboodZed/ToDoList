package com.abdallah.todolist.list;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdallah.todolist.R;
import com.abdallah.todolist.models.ToDoList;
import com.abdallah.todolist.models.ToDoTask;
import com.abdallah.todolist.utils.AppConstants;
import com.abdallah.todolist.utils.FirebaseReferences;
import com.abdallah.todolist.utils.TaskDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ListActivity extends AppCompatActivity implements TextWatcher {

    ImageView ivBack;
    ImageView ivSearch;
    LinearLayout llSearch;
    EditText etSearch;
    TextView tvListName;
    TextView tvListDelete;
    LinearLayout etCreate;
    RecyclerView rvTasks;

    private ToDoList toDoList;
    private DatabaseReference myRef;
    private TasksAdapter tasksAdapter;
    private boolean is_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toDoList = (ToDoList) getIntent().getSerializableExtra(AppConstants.LIST_EXTRA);
        myRef = FirebaseReferences.getListReference(this, toDoList.getId());
        bindViews();
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        ivSearch = findViewById(R.id.iv_search);
        llSearch = findViewById(R.id.ll_search);
        etSearch = findViewById(R.id.et_search);
        tvListName = findViewById(R.id.tv_list_name);
        tvListDelete = findViewById(R.id.tv_list_delete);
        etCreate = findViewById(R.id.et_create);
        rvTasks = findViewById(R.id.rv_tasks);

        tvListName.setText(toDoList.getName() + " :");

        ivBack.setOnClickListener(view -> onBackPressed());
        ivSearch.setOnClickListener(view -> {
            if (!is_search) {
                llSearch.setVisibility(View.VISIBLE);
                is_search = true;
            } else {
                llSearch.setVisibility(View.GONE);
                hideKeyboard(this);
                is_search = false;
            }
        });
        etSearch.addTextChangedListener(this);
        etCreate.setOnClickListener(view -> addTask());
        tvListDelete.setOnClickListener(view -> deleteList());

        tasksAdapter = new TasksAdapter(this, toDoList);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(tasksAdapter);
        setTasksListener();
    }

    public void addTask() {
        TaskDialog taskDialog = TaskDialog.newInstance(toDoList);
        taskDialog.show(getSupportFragmentManager(), "");
    }

    private void deleteList() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete))
                .setMessage(getString(R.string.delete_list))
                .setPositiveButton(R.string.yes, ((dialogInterface, i) ->
                        myRef.removeValue().addOnCompleteListener(task -> onBackPressed())))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void setTasksListener() {
        myRef.child(AppConstants.TABLE_TASKS_NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tasksAdapter.addItems(snapshot.getValue(ToDoTask.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tasksAdapter.updateItems(snapshot.getValue(ToDoTask.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                tasksAdapter.deleteItem(snapshot.getValue(ToDoTask.class));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        tasksAdapter.search(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (imm != null) {
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }
}