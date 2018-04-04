package com.example.franciscoandrade.bloxsee.views.student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Questions;
    import com.example.franciscoandrade.bloxsee.model.StudentInfo;
import com.example.franciscoandrade.bloxsee.views.StudentActivity;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Kevintoro on 3/18/18.
 */

public class StudentQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView studentQuestion;
    private TextView btm_line;
    private ImageView dot_item;
    private String studentName;
    CardView cardViewQuestions;
    Context context;

    public StudentQuestionViewHolder(final View itemView) {
        super(itemView);
        //dot_item = itemView.findViewById(R.id.question_dot_item);
        //btm_line = itemView.findViewById(R.id.question_btm_line);
        studentQuestion = itemView.findViewById(R.id.questions_tv);
        cardViewQuestions = itemView.findViewById(R.id.cardViewQuestions);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String currentQuestion = studentQuestion.getText().toString();
//                Intent intent = new Intent(view.getContext(), BlocklyActivity.class);
//                intent.putExtra("currentQuestion", currentQuestion);
//                intent.putExtra("studentName", studentName);
//                view.getContext().startActivity(intent);
//            }
//        });
    }
    public void onBind(StudentInfo studentInfo, int position, int size, String studentName, Context context){
        String sIQuestion = studentInfo.getQuestion();
        String sILesson = studentInfo.getLesson();
        String sIQuestionNum = studentInfo.getQuestionNum();

        Log.d("sihihi",sIQuestion);
        Log.d("sihihi",sILesson);
        Log.d("sihihi",sIQuestionNum);

        studentQuestion.setText(sIQuestion);
        this.studentName=studentName;
        this.context= context;

        cardViewQuestions.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cardViewQuestions:

                String currentQuestion = studentQuestion.getText().toString();

                //Log.d("mehihihi", )
                Intent intent = new Intent(context, BlocklyActivity.class);
                intent.putExtra("currentQuestion", currentQuestion);
                intent.putExtra("studentName", studentName);
                context.startActivity(intent);
                break;



        }

    }
}
