package com.abdallah.todolist.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdallah.todolist.R;
import com.abdallah.todolist.models.ToDoList;
import com.abdallah.todolist.models.ToDoTask;
import com.abdallah.todolist.task.TaskActivity;
import com.abdallah.todolist.utils.AppConstants;
import com.abdallah.todolist.utils.FirebaseReferences;

import java.util.ArrayList;

class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private ArrayList<ToDoTask> toDoTasks;
    private ArrayList<ToDoTask> all;
    private Activity activity;
    private ToDoList toDoList;

    public TasksAdapter(Activity activity, ToDoList toDoList) {
        this.activity = activity;
        this.toDoList = toDoList;
        toDoTasks = new ArrayList<>();
        all = new ArrayList<>();
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

    public void search(String s) {
        toDoTasks.clear();
        notifyDataSetChanged();
        for (ToDoTask toDoTask : all) {
            if (toDoTask.getName().contains(s)) {
                toDoTasks.add(toDoTask);
                notifyItemInserted(toDoTasks.size() - 1);
            }
        }
    }

    public void addItems(ToDoTask toDoTask) {
        toDoTasks.add(toDoTask);
        all.add(toDoTask);
        notifyItemInserted(getItemCount() - 1);
    }

    public void updateItems(ToDoTask value) {
        toDoTasks.clear();
        notifyDataSetChanged();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == value.getId()) {
                all.remove(all.get(i));
                all.add(i, value);
            }
            toDoTasks.add(all.get(i));
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void deleteItem(ToDoTask value) {
        toDoTasks.clear();
        notifyDataSetChanged();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getName().equals(value.getName())) {
                all.remove(all.get(i));
            } else {
                toDoTasks.add(all.get(i));
                notifyItemInserted(getItemCount() - 1);
            }
        }
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clBackground;
        AppCompatCheckBox cbFinished;
        TextView tvTaskName;
        TextView tvTaskType;

        ToDoTask toDoTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View view) {
            clBackground = view.findViewById(R.id.cl_background);
            cbFinished = view.findViewById(R.id.cb_finished);
            tvTaskName = view.findViewById(R.id.tv_task_name);
            tvTaskType = view.findViewById(R.id.tv_task_type);

            clBackground.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, TaskActivity.class);
                intent.putExtra(AppConstants.TASK_EXTRA, toDoTask);
                intent.putExtra(AppConstants.LIST_EXTRA, toDoList);
                activity.startActivity(intent);
            });
        }

        public void setData(ToDoTask toDoTask) {
            this.toDoTask = toDoTask;
            tvTaskName.setText("\t" + toDoTask.getName());
            tvTaskType.setText(toDoTask.getType());

            check(!toDoTask.isFinish());

            cbFinished.setOnClickListener(view -> {
                boolean b = cbFinished.isChecked();
                toDoTask.setFinish(b);
                FirebaseReferences.getTaskReference(activity, toDoList.getId(), toDoTask.getId())
                        .setValue(toDoTask).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        check(!b);
                    } else {
                        check(b);
                    }
                });
            });
        }

        private void check(boolean b) {
            if (b) {
                cbFinished.setChecked(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTaskName.setTextColor(activity.getColor(R.color.black));
                }
                tvTaskName.setPaintFlags(tvTaskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            } else {
                cbFinished.setChecked(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTaskName.setTextColor(activity.getColor(R.color.dark_gray));
                }
                tvTaskName.setPaintFlags(tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
