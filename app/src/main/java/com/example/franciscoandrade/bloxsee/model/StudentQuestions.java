package com.example.franciscoandrade.bloxsee.model;

import java.util.HashMap;

/**
 * Created by franciscoandrade on 3/18/18.
 */

public class StudentQuestions {
   private Boolean q1;
   private String state;

    public StudentQuestions() {
    }

    public StudentQuestions(Boolean q1, String state) {
        this.q1 = q1;
        this.state = state;
    }


    public Boolean getQ1() {
        return q1;
    }

    public void setQ1(Boolean q1) {
        this.q1 = q1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
