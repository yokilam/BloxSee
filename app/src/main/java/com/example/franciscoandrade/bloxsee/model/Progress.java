package com.example.franciscoandrade.bloxsee.model;

import android.widget.TextView;

public class Progress {
    private String name;
    private String questionOne;
    private String questionTwo;
    private String questionThree;
    private String questionFour;
    private String questionFive;

    public Progress(String name, String questionOne, String questionTwo, String questionThree, String questionFour, String questionFive) {
        this.name = name;
        this.questionOne = questionOne;
        this.questionTwo = questionTwo;
        this.questionThree = questionThree;
        this.questionFour = questionFour;
        this.questionFive = questionFive;
    }

    public String getName() {
        return name;
    }

    public String getQuestionOne() {
        return questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public String getQuestionThree() {
        return questionThree;
    }

    public String getQuestionFour() {
        return questionFour;
    }

    public String getQuestionFive() {
        return questionFive;
    }
}
