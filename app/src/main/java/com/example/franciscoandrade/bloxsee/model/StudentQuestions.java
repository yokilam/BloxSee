package com.example.franciscoandrade.bloxsee.model;

import java.util.HashMap;

/**
 * Created by franciscoandrade on 3/18/18.
 */

public class StudentQuestions {
   private Boolean available;
   private String state;
   private String question;



    public StudentQuestions() {
    }

    public StudentQuestions(Boolean available, String state, String question) {
        this.available = available;
        this.state = state;
        this.question = question;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
