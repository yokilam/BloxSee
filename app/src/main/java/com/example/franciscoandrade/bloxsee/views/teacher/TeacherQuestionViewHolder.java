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

import java.util.List;

/**
 * Created by yokilam on 3/18/18.
 */

public class TeacherQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView levelNumber;

    private SparseBooleanArray expandState = new SparseBooleanArray();
    ExpandableLayoutAnimation expandableLayoutAnimation;
    public ExpandableLinearLayout expandableLayout;
    private CheckBox checkBoxOne, checkBoxTwo, checkBoxThree, checkBoxFour, checkBoxFive;
    private List<Boolean> list1;
    private List<Boolean> list2;
    private List<Boolean> list3;


    public TeacherQuestionViewHolder(View itemView) {
        super(itemView);
        levelNumber= itemView.findViewById(R.id.level);

        checkBoxOne= itemView.findViewById(R.id.checkbox_one);
        checkBoxTwo= itemView.findViewById(R.id.checkbox_two);
        checkBoxThree= itemView.findViewById(R.id.checkbox_three);
        checkBoxFour= itemView.findViewById(R.id.checkbox_four);
        checkBoxFive= itemView.findViewById(R.id.checkbox_five);
        expandableLayout= itemView.findViewById(R.id.question_expandable_layout);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();


    }

    public void onBind(Level level, int position) {
        levelNumber.setText(level.getLevel());
        checkBoxOne.setText(level.getProblemOne());
        checkBoxTwo.setText(level.getProblemOne());
        checkBoxThree.setText(level.getProblemOne());
        checkBoxFour.setText(level.getProblemOne());
        checkBoxFive.setText(level.getProblemOne());
        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                (v, expandableLayout, R.color.material_deep_purple_100, expandState);
    }

}
