package com.abdallah.todolist.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ToDoList implements Serializable {

    private String id;
    private String name;
    private ArrayList<ToDoTask> toDoTasks;

    public ToDoList() {
    }

    public ToDoList(String id, String name) {
        this.id = id;
        this.name = name;
        toDoTasks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ToDoTask> getToDoTasks() {
        if (toDoTasks == null)
            toDoTasks = new ArrayList<>();
        return toDoTasks;
    }

    public void setToDoTasks(ArrayList<ToDoTask> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", toDoTasks=" + toDoTasks +
                '}';
    }
}
