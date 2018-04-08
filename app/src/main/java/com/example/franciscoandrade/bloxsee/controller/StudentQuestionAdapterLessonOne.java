package com.example.franciscoandrade.bloxsee.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.StudentInfo;
import com.example.franciscoandrade.bloxsee.views.student.StudentQuestionLessonOneViewHolder;
import com.example.franciscoandrade.bloxsee.views.student.StudentQuestionViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yokilam on 4/7/18.
 */

public class StudentQuestionAdapterLessonOne extends RecyclerView.Adapter<StudentQuestionLessonOneViewHolder> {

    private List<StudentInfo> questionsList;
    private Context context;
    private String studentName;

    public StudentQuestionAdapterLessonOne(Context context, String studentName) {
        this.context=context;
        questionsList= new ArrayList<>();
        this.studentName= studentName;

    }

    @Override
    public StudentQuestionLessonOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_question_itemviewone, parent, false);
        return new StudentQuestionLessonOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentQuestionLessonOneViewHolder holder, int position) {
        holder.onBind(questionsList.get(position), position, questionsList.size(), studentName, context);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public void addQuestions(List<StudentInfo> questions) {
        questionsList.clear();
        questionsList.addAll(questions);
        notifyDataSetChanged();
    }
}
