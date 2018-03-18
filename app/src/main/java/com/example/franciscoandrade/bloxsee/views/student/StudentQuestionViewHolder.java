package com.example.franciscoandrade.bloxsee.views.student;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Questions;

/**
 * Created by Kevintoro on 3/18/18.
 */

public class StudentQuestionViewHolder extends RecyclerView.ViewHolder {
    private TextView studentQuestion;

    public StudentQuestionViewHolder(View itemView) {
        super(itemView);
        studentQuestion = itemView.findViewById(R.id.questions_tv);
    }
    public void onBind(Questions questions){
        studentQuestion.setText(questions.getQuestions());

    }
}
