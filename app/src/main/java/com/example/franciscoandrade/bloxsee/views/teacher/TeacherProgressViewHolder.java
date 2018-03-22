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
    private TextView lessonOne, lessonTwo;
    private TextView questionOne, questionTwo, questionThree, questionFour, questionFive;
    private ExpandableLinearLayout expandableLayout;
    ExpandableLayoutAnimation expandableLayoutAnimation;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    Progress progress1;

    public TeacherProgressViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_progress);
        lessonOne = itemView.findViewById(R.id.lesson_one);
        lessonTwo = itemView.findViewById(R.id.lesson_two);
        questionOne= itemView.findViewById(R.id.question_one);
        questionTwo= itemView.findViewById(R.id.question_two);
        questionThree= itemView.findViewById(R.id.question_three);
        questionFour= itemView.findViewById(R.id.question_four);
        questionFive= itemView.findViewById(R.id.question_five);

        expandableLayout = itemView.findViewById(R.id.progress_expandableLayout);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

        lessonOne.setOnClickListener(this);
        lessonTwo.setOnClickListener(this);

    }

    public void onBind(Progress progress) {
        name.setText(progress.getName());
        progress1= progress;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lesson_one:
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
                break;
            case R.id.lesson_two:
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, expandableLayout, R.color.material_blue_50, expandState);
                changeColor(progress1.getLesson2().get(0).getState(), questionOne);
                changeColor(progress1.getLesson2().get(1).getState(), questionTwo);
                changeColor(progress1.getLesson2().get(2).getState(), questionThree);
                changeColor(progress1.getLesson2().get(3).getState(), questionFour);
                changeColor(progress1.getLesson2().get(4).getState(), questionFive);
                Log.d("COLORS2==", "onClick: "+progress1.getLesson2().get(0));
                Log.d("COLORS2==", "onClick: "+progress1.getLesson2().get(1));
                Log.d("COLORS2==", "onClick: "+progress1.getLesson2().get(2));
                Log.d("COLORS2==", "onClick: "+progress1.getLesson2().get(3));
                Log.d("COLORS2==", "onClick: "+progress1.getLesson2().get(4));


                break;

        }
    }

    public void changeColor(String string, TextView textView){
        if (string.equals("passed")) {
            textView.setBackgroundResource(R.color.material_red_500);
        }
        if (string.equals("failed")) {
            textView.setBackgroundResource(R.color.material_green_300);
        }
    }
}
