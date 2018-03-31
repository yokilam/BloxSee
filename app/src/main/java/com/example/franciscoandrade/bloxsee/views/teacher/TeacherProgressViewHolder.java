package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

public class TeacherProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView lessonOne;
    private TextView questionOne, questionTwo, questionThree, questionFour, questionFive;
    private ExpandableLinearLayout expandableLayout;
    private ExpandableLinearLayout expandableLinearLayoutQuestions;
    private ExpandableLayoutAnimation expandableLayoutAnimation;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Progress progress1;
    Context context;

    public TeacherProgressViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_progress);
        lessonOne = itemView.findViewById(R.id.lessons);
        questionOne= itemView.findViewById(R.id.lesson_one);
        questionTwo= itemView.findViewById(R.id.lesson_two);
        questionThree= itemView.findViewById(R.id.lesson_three);
        questionFour= itemView.findViewById(R.id.lesson_four);
        questionFive= itemView.findViewById(R.id.lesson_five);

        expandableLayout = itemView.findViewById(R.id.progress_expandableLayout);
        expandableLinearLayoutQuestions = itemView.findViewById(R.id.progress_questions_expandableLayout);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

        lessonOne.setOnClickListener(this);

        questionOne.setOnClickListener(this);
        questionTwo.setOnClickListener(this);
        questionThree.setOnClickListener(this);
        questionFour.setOnClickListener(this);
        questionFive.setOnClickListener(this);

    }

    public void onBind(Progress progress, Context context) {
        name.setText(progress.getName());
        progress1= progress;
        this.context=context;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.lessons:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_orange_200, expandState);
                changeColor(progress1.getLesson1().get(0).getState(), questionOne);
                changeColor(progress1.getLesson1().get(1).getState(), questionTwo);
                changeColor(progress1.getLesson1().get(2).getState(), questionThree);
                changeColor(progress1.getLesson1().get(3).getState(), questionFour);
                changeColor(progress1.getLesson1().get(4).getState(), questionFive);
                Log.d("COLORS1==", "onClick: "+progress1.getLesson1().get(0));
                Log.d("COLORS1==", "onClick: "+progress1.getLesson1().get(1));
                Log.d("COLORS1==", "onClick: "+progress1.getLesson1().get(2));
                Log.d("COLORS1==", "onClick: "+progress1.getLesson1().get(3));
                Log.d("COLORS1==", "onClick: "+progress1.getLesson1().get(4));

                if(expandableLinearLayoutQuestions.isExpanded()){
                    expandableLinearLayoutQuestions.collapse();
                }
                break;
            case R.id.lesson_one:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
                break;
            case R.id.lesson_two:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
                break;
            case R.id.lesson_three:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
                break;
            case R.id.lesson_four:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
                break;
            case R.id.lesson_five:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
                break;

        }

    }

    public void changeColor(String string, TextView textView){
        if (string.equals("passed")) {
            textView.setBackgroundResource(R.color.material_red_500);
            textView.setTextColor(context.getResources().getColor(R.color.material_red_500));
        }
        if (string.equals("failed")) {
            textView.setBackgroundResource(R.color.material_green_300);
            textView.setTextColor(context.getResources().getColor(R.color.material_green_300    ));

        }
    }
}
