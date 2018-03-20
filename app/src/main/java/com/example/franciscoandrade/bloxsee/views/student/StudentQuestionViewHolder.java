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




    public StudentQuestionViewHolder(final View itemView) {
        super(itemView);
        studentQuestion = itemView.findViewById(R.id.questions_tv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BlocklyActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
    public void onBind(String questions){
        studentQuestion.setText(questions);


    }
}
