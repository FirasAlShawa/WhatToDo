package com.firasshawa.whattodo.Models;

public class Todo {
    private String key ;
    private String todoBody ;
    private String timeStamp ;
    private String state ;

    public Todo(String key, String todoBody, String timeStamp, String state) {
        this.key = key;
        this.todoBody = todoBody;
        this.timeStamp = timeStamp;
        this.state = state;
    }

    public Todo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTodoBody() {
        return todoBody;
    }

    public void setTodoBody(String todoBody) {
        this.todoBody = todoBody;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "key='" + key + '\'' +
                ", todoBody='" + todoBody + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
