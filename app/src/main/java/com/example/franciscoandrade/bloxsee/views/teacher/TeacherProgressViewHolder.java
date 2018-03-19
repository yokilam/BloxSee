package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

public class TeacherProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView lessonOne, lessonTwo, lessonThree, lessonFour, lessonFive;
    private TextView questionOne, questionTwo, questionThree, questionFour, questionFive;
    private ExpandableLinearLayout expandableLayout;
    ExpandableLayoutAnimation expandableLayoutAnimation;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public TeacherProgressViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_progress);
        lessonOne = itemView.findViewById(R.id.lesson_one);
        lessonTwo = itemView.findViewById(R.id.lesson_two);
        lessonThree = itemView.findViewById(R.id.lesson_three);
        lessonFour = itemView.findViewById(R.id.lesson_four);
        lessonFive = itemView.findViewById(R.id.lesson_five);
        questionOne= itemView.findViewById(R.id.question_one);
        questionTwo= itemView.findViewById(R.id.question_two);
        questionThree= itemView.findViewById(R.id.question_three);
        questionFour= itemView.findViewById(R.id.question_four);
        questionFive= itemView.findViewById(R.id.question_five);

        expandableLayout = itemView.findViewById(R.id.progress_expandableLayout);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

        lessonOne.setOnClickListener(this);
        lessonTwo.setOnClickListener(this);
        lessonThree.setOnClickListener(this);
        lessonFour.setOnClickListener(this);
        lessonFive.setOnClickListener(this);
    }

    public void onBind(Progress progress) {
        name.setText(progress.getName());
        changeColor(progress.getQuestionOne(), questionOne);
        changeColor(progress.getQuestionTwo(), questionTwo);
        changeColor(progress.getQuestionThree(), questionThree);
        changeColor(progress.getQuestionFour(), questionFour);
        changeColor(progress.getQuestionFive(), questionFive);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lesson_one:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_orange_200, expandState);
                break;
            case R.id.lesson_two:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_blue_50, expandState);
                break;
            case R.id.lesson_three:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_deep_purple_100, expandState);
                break;
            case R.id.lesson_four:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_orange_400 ,expandState);
                break;
            case R.id.lesson_five:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_blue_400, expandState);
                break;
        }
    }

    public void changeColor(String string, TextView textView){
        if (string.equals("wrong")) {
            textView.setBackgroundResource(R.color.material_red_500);
        } else if (string.equals("right")) {
            textView.setBackgroundResource(R.color.material_green_300);
        }
    }
}
