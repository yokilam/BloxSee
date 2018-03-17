package com.example.franciscoandrade.bloxsee.model;

/**
 * Created by franciscoandrade on 3/17/18.
 */

public class Student {
    private String name;
    private String password;

    public Student() {
    }

    public Student(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
