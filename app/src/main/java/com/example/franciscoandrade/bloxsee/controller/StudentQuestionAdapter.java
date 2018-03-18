package com.example.franciscoandrade.bloxsee.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.views.student.StudentQuestionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevintoro on 3/18/18.
 */

public class StudentQuestionAdapter extends RecyclerView.Adapter<StudentQuestionViewHolder> {

    public List<Questions> questionsList = new ArrayList<>();

    public StudentQuestionAdapter(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    @Override
    public StudentQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_question_itemview, parent, false);
        return new StudentQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentQuestionViewHolder holder, int position) {
        holder.onBind(getQuestionsList().get(position));
    }

    @Override
    public int getItemCount() {
        return getQuestionsList().size();
    }
    public List <Questions> getQuestionsList(){
        return questionsList;
    }
}
