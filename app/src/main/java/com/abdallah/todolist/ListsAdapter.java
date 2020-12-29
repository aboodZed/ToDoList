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
    private ArrayList<ToDoList> all;
    private Activity activity;

    public ListsAdapter(Activity activity) {
        this.activity = activity;
        toDoLists = new ArrayList<>();
        all = new ArrayList<>();
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

    public void addItem(ToDoList toDoList) {
        toDoLists.add(toDoList);
        all.add(toDoList);
        notifyItemInserted(toDoLists.size() - 1);
    }

    public void addAll() {
        toDoLists = all;
        notifyDataSetChanged();
    }

    public void search(String s) {
        toDoLists.clear();
        notifyDataSetChanged();
        for (ToDoList toDoList : all) {
            if (toDoList.getName().contains(s)) {
                toDoLists.add(toDoList);
                notifyItemInserted(toDoLists.size() - 1);
            }
        }
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
            if (toDoList.getToDoTasks() != null)
                tvTasksNumber.setText(toDoList.getToDoTasks().size() + " tasks");

            clBackground.setOnClickListener(view -> {
                Intent intent = new Intent(activity, ListActivity.class);
                intent.putExtra(AppConstants.LIST_EXTRA, toDoList);
                activity.startActivity(intent);
            });
        }
    }
}
