package com.abdallah.todolist.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.abdallah.todolist.auth.LoginActivity;
import com.abdallah.todolist.R;
import com.abdallah.todolist.models.ToDoList;
import com.abdallah.todolist.utils.FirebaseReferences;
import com.abdallah.todolist.utils.SharedPreference;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private ImageView ivBack;
    private TextView tvLogout;
    private EditText etSearch;
    private LinearLayout etCreate;
    private RecyclerView rvLists;

    private ListsAdapter listsAdapter;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRef = FirebaseReferences.getUserListsReference(this);
        bindViews();
    }

    private void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        tvLogout = findViewById(R.id.tv_logout);
        etSearch = findViewById(R.id.et_search);
        etCreate = findViewById(R.id.et_create);
        rvLists = findViewById(R.id.rv_lists);

        ivBack.setOnClickListener(view -> onBackPressed());
        tvLogout.setOnClickListener(view -> {
            new SharedPreference(this).clear();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        etCreate.setOnClickListener(view -> addList());

        etSearch.addTextChangedListener(this);
        recycle();
        addFireBaseListener();
    }

    private void recycle() {
        listsAdapter = new ListsAdapter(this);
        rvLists.setLayoutManager(new LinearLayoutManager(this));
        rvLists.setItemAnimator(new DefaultItemAnimator());
        rvLists.setAdapter(listsAdapter);
    }

    public void addList() {
        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new List")
                .setMessage("What do you want to name it?")
                .setView(editText)
                .setPositiveButton("Create", (dialog1, which) -> {

                    String listName = editText.getText().toString().trim();

                    if (TextUtils.isEmpty(listName)) {
                        editText.setError(getString(R.string.required_field));
                    } else {
                        String id = myRef.push().getKey();
                        ToDoList toDoList = new ToDoList(id, listName);
                        myRef.child(id).setValue(toDoList);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    private void addFireBaseListener() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listsAdapter.addItem(snapshot.getValue(ToDoList.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listsAdapter.updateItem(snapshot.getValue(ToDoList.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                listsAdapter.deleteItem(snapshot.getValue(ToDoList.class));
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
        listsAdapter.search(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}