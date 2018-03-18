package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Level;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

/**
 * Created by yokilam on 3/18/18.
 */

public class TeacherQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView levelNumber;
    private TextView problemOne, problemTwo, problemThree, problemFour, problemFive;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    ExpandableLayoutAnimation expandableLayoutAnimation;
    public ExpandableLinearLayout expandableLayout;
    private CheckBox checkBoxOne;

    public TeacherQuestionViewHolder(View itemView) {
        super(itemView);
        levelNumber= itemView.findViewById(R.id.level);
        problemOne= itemView.findViewById(R.id.problem_one);
        problemTwo= itemView.findViewById(R.id.problem_two);
        problemThree= itemView.findViewById(R.id.problem_three);
        problemFour= itemView.findViewById(R.id.problem_four);
        problemFive= itemView.findViewById(R.id.problem_five);
        checkBoxOne= itemView.findViewById(R.id.checkbox_one);
        expandableLayout= itemView.findViewById(R.id.question_expandable_layout);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();
    }

    public void onBind(Level level) {
        levelNumber.setText(level.getLevel());
        problemOne.setText(level.getProblemOne());
        problemTwo.setText(level.getProblemTwo());
        problemThree.setText(level.getProblemThree());
        problemFour.setText(level.getProblemFour());
        problemFive.setText(level.getProblemFive());
        checkBoxOne.setText(level.getProblemOne());
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                (v, expandableLayout, R.color.material_deep_purple_100, expandState);
    }

}
