package com.abdallah.todolist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListHolder> {

    private ArrayList<ToDoList> toDoLists;
    private Activity activity;

    public ListsAdapter(ArrayList<ToDoList> toDoLists, Activity activity) {
        this.toDoLists = toDoLists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHolder(LayoutInflater.from(activity)
                .inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.setData(toDoLists.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {

        TextView tvListName;
        TextView tvTasksNumber;
        ConstraintLayout clBackground;

        private ToDoList toDoList;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View view) {
            tvListName = view.findViewById(R.id.tv_list_name);
            tvTasksNumber = view.findViewById(R.id.tv_tasks_number);
            clBackground = view.findViewById(R.id.cl_background);
        }

        public void setData(ToDoList toDoList) {
            this.toDoList = toDoList;
            tvListName.setText(toDoList.getName());
            tvTasksNumber.setText(toDoList.getTasks() + " tasks");

            clBackground.setOnClickListener(view -> {
                Intent intent = new Intent(activity, ListActivity.class);
                intent.putExtra("list_id", toDoList.getId());
                activity.startActivity(intent);
            });
        }
    }
}
