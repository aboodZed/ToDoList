package com.abdallah.todolist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{

    private ArrayList<ToDoTask> toDoTasks;
    private Activity activity;

    public SearchAdapter(ArrayList<ToDoTask> toDoTasks, Activity activity) {
        this.toDoTasks = toDoTasks;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.SearchHolder(LayoutInflater.from(activity)
                .inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchHolder holder, int position) {
        holder.setData(toDoTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoTasks.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clBackground;
        AppCompatCheckBox cbFinished;
        TextView tvTaskName;
        TextView tvTaskType;

        ToDoTask toDoTask;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View view) {
            clBackground = view.findViewById(R.id.cl_background);
            cbFinished = view.findViewById(R.id.cb_finished);
            tvTaskName =  view.findViewById(R.id.tv_task_name);
            tvTaskType =  view.findViewById(R.id.tv_task_type);

            clBackground.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, TaskActivity.class);
                intent.putExtra("task", toDoTask);
                activity.startActivity(intent);
            });
        }

        public void setData(ToDoTask toDoTask) {
            this.toDoTask = toDoTask;
            tvTaskName.setText(toDoTask.getName());
            tvTaskType.setText(toDoTask.getType());
        }
    }
}
