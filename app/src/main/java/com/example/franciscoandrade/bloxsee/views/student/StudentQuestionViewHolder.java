package com.example.franciscoandrade.bloxsee.views.student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.views.StudentActivity;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Kevintoro on 3/18/18.
 */

public class StudentQuestionViewHolder extends RecyclerView.ViewHolder {
    private TextView studentQuestion;
    private TextView btm_line;
    private ImageView dot_item;

    public StudentQuestionViewHolder(final View itemView) {
        super(itemView);
        dot_item = itemView.findViewById(R.id.question_dot_item);
        btm_line = itemView.findViewById(R.id.question_btm_line);
        studentQuestion = itemView.findViewById(R.id.questions_tv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuestion = studentQuestion.getText().toString();
                Intent intent = new Intent(view.getContext(), BlocklyActivity.class);
                intent.putExtra("currentQuestion", currentQuestion);
                view.getContext().startActivity(intent);
            }
        });
    }
    public void onBind(String questions, int position, int size){
        studentQuestion.setText(questions);
        if(position==size-1){
            btm_line.setVisibility(View.INVISIBLE);
        }

    }
}
