package com.example.franciscoandrade.bloxsee.model;

import java.util.HashMap;

/**
 * Created by franciscoandrade on 3/18/18.
 */

public class StudentQuestions {
   private Boolean q1;
   private Boolean q2;
   private Boolean q3;
   private Boolean q4;
   private Boolean q5;

    public StudentQuestions() {
    }

    public StudentQuestions(Boolean q1, Boolean q2, Boolean q3, Boolean q4, Boolean q5) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }

    public Boolean getQ1() {
        return q1;
    }

    public void setQ1(Boolean q1) {
        this.q1 = q1;
    }

    public Boolean getQ2() {
        return q2;
    }

    public void setQ2(Boolean q2) {
        this.q2 = q2;
    }

    public Boolean getQ3() {
        return q3;
    }

    public void setQ3(Boolean q3) {
        this.q3 = q3;
    }

    public Boolean getQ4() {
        return q4;
    }

    public void setQ4(Boolean q4) {
        this.q4 = q4;
    }

    public Boolean getQ5() {
        return q5;
    }

    public void setQ5(Boolean q5) {
        this.q5 = q5;
    }
}
