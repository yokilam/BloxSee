package com.example.franciscoandrade.bloxsee.model;

/**
 * Created by joannesong on 4/3/18.
 */

public class StudentInfo {
    private String name;
    private String lesson;
    private String question;
    private String questionNum;

    public StudentInfo() {
    }

    public StudentInfo(String lesson, String question, String name, String questionNum) {
        this.lesson = lesson;
        this.question = question;
        this.questionNum = questionNum;
        this.question = question;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }
}
