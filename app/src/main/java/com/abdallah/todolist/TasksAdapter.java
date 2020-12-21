package com.abdallah.todolist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private ArrayList<ToDoTask> toDoTasks;
    private Activity activity;

    public TasksAdapter(ArrayList<ToDoTask> toDoTasks, Activity activity) {
        this.toDoTasks = toDoTasks;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(activity)
                .inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.setData(toDoTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoTasks.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clBackground;
        CheckBox cbFinished;

        ToDoTask toDoTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View view) {
            clBackground = view.findViewById(R.id.cl_background);
            cbFinished = view.findViewById(R.id.cb_finished);

            clBackground.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, TaskActivity.class);
                intent.putExtra("task", toDoTask);
                activity.startActivity(intent);
            });
        }

        public void setData(ToDoTask toDoTask) {
            this.toDoTask = toDoTask;
            cbFinished.setText("\t" + toDoTask.getName());
        }
    }
}
