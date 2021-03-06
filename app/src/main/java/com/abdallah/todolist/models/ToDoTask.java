package com.abdallah.todolist.models;

import java.io.Serializable;

public class ToDoTask implements Serializable {

    private int id;
    private String name;
    private String type;
    private long date;
    private String details;
    private boolean finish;

    public ToDoTask() {
    }

    public ToDoTask(int id, String name, String type, long date, String details) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "ToDoTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", details='" + details + '\'' +
                ", finish=" + finish +
                '}';
    }
}
