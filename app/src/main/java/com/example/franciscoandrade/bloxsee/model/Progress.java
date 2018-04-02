package com.example.franciscoandrade.bloxsee.model;

import android.widget.TextView;

import java.util.List;

public class Progress {
    private String name;
    private List<StudentQuestions> lesson1;
    private List<StudentQuestions> lesson2;
    private String password;


    public Progress() {
    }

    public Progress(String name, List<StudentQuestions> lesson1, List<StudentQuestions> lesson2, String password) {
        this.name = name;
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
        this.password= password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentQuestions> getLesson1() {
        return lesson1;
    }

    public void setLesson1(List<StudentQuestions> lesson1) {
        this.lesson1 = lesson1;
    }

    public List<StudentQuestions> getLesson2() {
        return lesson2;
    }

    public void setLesson2(List<StudentQuestions> lesson2) {
        this.lesson2 = lesson2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
