package com.example.franciscoandrade.bloxsee.views.teacher;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Progress;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TeacherProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name;
    private TextView questionOne, questionTwo, questionThree, questionFour, questionFive;
    private ImageView q1, q2, q3, q4, q5;

    private ExpandableLayoutAnimation expandableLayoutAnimation;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Progress progress1;
    Context context;

    CardView cardLessons;
    LinearLayout questionsView;
    CardView name_Student_Card;

    int average=100;

    public TeacherProgressViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_progress);
        questionOne= itemView.findViewById(R.id.lesson_1);
        questionTwo= itemView.findViewById(R.id.lesson_two);
        questionThree= itemView.findViewById(R.id.lesson_three);
        questionFour= itemView.findViewById(R.id.lesson_four);
        q1= itemView.findViewById(R.id.q1);
        q2= itemView.findViewById(R.id.q2);
        q3= itemView.findViewById(R.id.q3);
        q4= itemView.findViewById(R.id.q4);
        q5= itemView.findViewById(R.id.q5);
        name_Student_Card= itemView.findViewById(R.id.name_Student_Card );


        cardLessons= itemView.findViewById(R.id.cardLessons);
        questionsView= itemView.findViewById(R.id.questionsView);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

        questionOne.setOnClickListener(this);
        questionTwo.setOnClickListener(this);
        questionThree.setOnClickListener(this);
        questionFour.setOnClickListener(this);
        //questionFive.setOnClickListener(this);
        cardLessons.setOnClickListener(this);

    }

    public void onBind(Progress progress, Context context) {
        name.setText(progress.getName());
        progress1= progress;
        this.context=context;

        calculateAVerage();

    }

    private void calculateAVerage() {
        List<String> lesson1= new ArrayList<>();
        List<String> lesson2= new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            lesson1.add(progress1.getLesson1().get(i).getState());


            lesson2.add(progress1.getLesson1().get(i).getState());
        }


        for (int i = 0; i < 5; i++) {
            Log.d("AVERAGE=", "calculateAVerage: "+progress1.getName()+" - "+lesson1.get(i));
            Log.d("AVERAGE=", "calculateAVerage: "+progress1.getName()+" - "+lesson2.get(i));
            if(lesson1.get(i).equals("passed")){

                average=average;
            }

            if (lesson2.get(i).equals("passed")){
                average=average;
            }

            if(lesson1.get(i).equals("failed") || lesson2.get(i).equals("failed") || lesson1.get(i).equals("fail")|| lesson2.get(i).equals("fail")){
                average-=10;
            }


        }

            Log.d("AVERAGE=", "calculateAVerage: "+progress1.getName()+" - "+average);


        setColorAverage();

    }

    private void setColorAverage() {

        if(average>=80){
            name_Student_Card.setCardBackgroundColor(Color.parseColor("#26A69A"));
        }


        if(average>=60 && average<80){
            name_Student_Card.setCardBackgroundColor(Color.parseColor("#ffc910"));
        }

        if(average<59 ){
            name_Student_Card.setCardBackgroundColor(Color.parseColor("#FF3333"));
        }



    }

    @Override
    public void onClick(View v) {



        switch(v.getId()){
            case R.id.lesson_1:
//                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        //(v, expandableLayout, R.color.material_orange_200, expandState);
                changeColor(progress1.getLesson1().get(0).getState(), q1);
                changeColor(progress1.getLesson1().get(1).getState(), q2);
                changeColor(progress1.getLesson1().get(2).getState(), q3);
                changeColor(progress1.getLesson1().get(3).getState(), q4);
                changeColor(progress1.getLesson1().get(4).getState(), q5);
                Log.d("COLORS1==", "onBind: "+progress1.getName());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().size());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getAvailable());


                break;
            case R.id.lesson_two:
                changeColor(progress1.getLesson2().get(0).getState(), q1);
                changeColor(progress1.getLesson2().get(1).getState(), q2);
                changeColor(progress1.getLesson2().get(2).getState(), q3);
                changeColor(progress1.getLesson2().get(3).getState(), q4);
                changeColor(progress1.getLesson2().get(4).getState(), q5);


                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getQuestion());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getState());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getAvailable());
                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getAvailable());
                break;
//            case R.id.lesson_three:
//                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
//                break;
//            case R.id.lesson_four:
//                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
//                break;
//            case R.id.lesson_five:
//                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation(v, expandableLinearLayoutQuestions, R.color.material_blue_100, expandState);
//                break;

            case R.id.cardLessons:

                if (questionsView.getVisibility()==View.GONE){
                    questionsView.setVisibility(View.VISIBLE);
                }else {
                    questionsView.setVisibility(View.GONE);
                }
                break;
        }

    }

    public void changeColor(String string, ImageView imageView){
        if (string.equals("passed")) {
            imageView.setColorFilter(context.getResources().getColor(R.color.main_blue));
        }
        if (string.equals("failed")) {
            imageView.setColorFilter(context.getResources().getColor(R.color.material_red_500));
        }
    }
}