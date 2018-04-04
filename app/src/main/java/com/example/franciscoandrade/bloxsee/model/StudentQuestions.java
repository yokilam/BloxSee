package com.example.franciscoandrade.bloxsee.model;

import java.util.HashMap;

/**
 * Created by franciscoandrade on 3/18/18.
 */

public class StudentQuestions {
   private Boolean available;
   private String state;
   private String question;
   private String answer;

    public StudentQuestions() {
    }

    public StudentQuestions(Boolean available, String state, String question, String answer) {
        this.available = available;
        this.state = state;
        this.question = question;
        this.answer= answer;
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


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}



