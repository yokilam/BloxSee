package com.example.franciscoandrade.bloxsee.model;

import android.widget.TextView;

/**
 * Created by yokilam on 3/18/18.
 */

public class Level {
    private String level;
    private String problemOne;
    private String problemTwo;
    private String problemThree;
    private String problemFour;
    private String problemFive;

    public Level(String level, String problemOne, String problemTwo, String problemThree, String problemFour, String problemFive) {
        this.level = level;
        this.problemOne = problemOne;
        this.problemTwo = problemTwo;
        this.problemThree = problemThree;
        this.problemFour = problemFour;
        this.problemFive = problemFive;
    }

    public String getLevel() {
        return level;
    }

    public String getProblemOne() {
        return problemOne;
    }

    public String getProblemTwo() {
        return problemTwo;
    }

    public String getProblemThree() {
        return problemThree;
    }

    public String getProblemFour() {
        return problemFour;
    }

    public String getProblemFive() {
        return problemFive;
    }
}
