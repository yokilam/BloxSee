package com.example.franciscoandrade.bloxsee.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.views.student.StudentQuestionViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StudentQuestionAdapter extends RecyclerView.Adapter<StudentQuestionViewHolder> {

    private List<String> questionsList;
    private Context context;
    private String studentName;


    public StudentQuestionAdapter(Context context, String studentName) {
        this.context=context;
        questionsList= new ArrayList<>();
        this.studentName= studentName;
    }

    @Override
    public StudentQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_question_itemview, parent, false);
        return new StudentQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentQuestionViewHolder holder, int position) {
        holder.onBind(questionsList.get(position), position, questionsList.size(), studentName, context);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public void addQuestions(List<String> questions) {
        questionsList.clear();
        questionsList.addAll(questions);
        notifyDataSetChanged();
    }
}
