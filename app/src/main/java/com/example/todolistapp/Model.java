package com.example.todolistapp;

public class Model {
    private String task;
    private String description;
    private String id;
    private String date;


    public Model() {
    }
    public Model(String task, String description, String id, String date) {
        this.task = task;
        this.description = description;
        this.id = id;
        this.date = date;

    }

    public String getTask() {
        return task;
    }

    public Model setTask(String task) {
        this.task = task;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Model setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getId() {
        return id;
    }

    public Model setId(String id) {
        this.id = id;
        return this;
    }

    public String getDate() {
        return date;
    }



    public Model setDate(String date) {
        this.date = date;
        return this;
    }
}
