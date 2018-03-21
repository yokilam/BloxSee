package com.example.franciscoandrade.bloxsee.model;

import android.widget.TextView;

import java.util.List;

public class Progress {
    private String name;
    private List<String> lesson1;
    private List<String> lesson2;


    public Progress() {
    }

    public Progress(String name, List<String> lesson1, List<String> lesson2) {
        this.name = name;
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLesson1() {
        return lesson1;
    }

    public void setLesson1(List<String> lesson1) {
        this.lesson1 = lesson1;
    }

    public List<String> getLesson2() {
        return lesson2;
    }

    public void setLesson2(List<String> lesson2) {
        this.lesson2 = lesson2;
    }
}
