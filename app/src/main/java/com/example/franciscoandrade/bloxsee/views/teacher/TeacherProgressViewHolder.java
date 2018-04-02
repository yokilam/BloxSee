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
import com.example.franciscoandrade.bloxsee.views.student.UrlListener;
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

    ImageView dotSelectorOne, dotSelectorTwo, dotSelectorThree, dotSelectorFour, dotSelectorFive;
    LinearLayout option_Lesson_One, option_Lesson_Two, option_Lesson_Three, option_Lesson_Four, option_Lesson_Five;

    int average=100;
    String imageName;
    String currentStudentName;
    String currentLesson;
    String currentQuestion;
    UrlListener urlListener;

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
        dotSelectorOne= itemView.findViewById(R.id.dotSelectorOne );
        dotSelectorTwo= itemView.findViewById(R.id.dotSelectorTwo );
        dotSelectorThree= itemView.findViewById(R.id.dotSelectorThree );
        dotSelectorFour= itemView.findViewById(R.id.dotSelectorFour );
        dotSelectorFive= itemView.findViewById(R.id.dotSelectorFive );
        option_Lesson_One= itemView.findViewById(R.id.option_Lesson_One );
        option_Lesson_Two= itemView.findViewById(R.id.option_Lesson_Two );
        option_Lesson_Three= itemView.findViewById(R.id.option_Lesson_Three );
        option_Lesson_Four= itemView.findViewById(R.id.option_Lesson_Four );
        option_Lesson_Five= itemView.findViewById(R.id.option_Lesson_Five );


        cardLessons= itemView.findViewById(R.id.cardLessons);
        questionsView= itemView.findViewById(R.id.questionsView);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();

//        questionOne.setOnClickListener(this);
//        questionTwo.setOnClickListener(this);
//        questionThree.setOnClickListener(this);
//        questionFour.setOnClickListener(this);
        //questionFive.setOnClickListener(this);
        //cardLessons.setOnClickListener(this);





        option_Lesson_One.setOnClickListener(this);
        option_Lesson_Two.setOnClickListener(this);
        option_Lesson_Three.setOnClickListener(this);
        option_Lesson_Four.setOnClickListener(this);
        option_Lesson_Five.setOnClickListener(this);



        //question screenshot listener
        q1.setOnClickListener(this);
        q2.setOnClickListener(this);
        q3.setOnClickListener(this);
        q4.setOnClickListener(this);
        q5.setOnClickListener(this);
    }

    public void onBind(Progress progress, Context context) {
        name.setText(progress.getName());
        progress1= progress;
        this.context=context;
        questionsView.setVisibility(View.GONE);
        calculateAVerage();
        uncheckSelectors();

        currentStudentName=progress1.getName();

        urlListener=(UrlListener)context;

    }

    private void uncheckSelectors() {
//        dotSelectorOne.setColorFilter(context.getResources().getColor(R.color.gray_selector));
//        dotSelectorTwo.setColorFilter(context.getResources().getColor(R.color.gray_selector));
//        dotSelectorThree.setColorFilter(context.getResources().getColor(R.color.gray_selector));
//        dotSelectorFour.setColorFilter(context.getResources().getColor(R.color.gray_selector));
//        dotSelectorFive.setColorFilter(context.getResources().getColor(R.color.gray_selector));


        dotSelectorOne.setVisibility(View.INVISIBLE);
        dotSelectorTwo.setVisibility(View.INVISIBLE);
        dotSelectorThree.setVisibility(View.INVISIBLE);
        dotSelectorFour.setVisibility(View.INVISIBLE);
        dotSelectorFive.setVisibility(View.INVISIBLE);


    }

    private void calculateAVerage() {
        List<String> lesson1= new ArrayList<>();
        List<String> lesson2= new ArrayList<>();


       if (progress1.getLesson1().size()!=0 &&progress1.getLesson2().size()!=0){



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

    }

    private void setColorAverage() {

        if(average>=80){
            name_Student_Card.setCardBackgroundColor(Color.parseColor("#ff66bb6a"));
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
//            case R.id.lesson_1:
////                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
//                        //(v, expandableLayout, R.color.material_orange_200, expandState);
//                changeColor(progress1.getLesson1().get(0).getState(), q1);
//                changeColor(progress1.getLesson1().get(1).getState(), q2);
//                changeColor(progress1.getLesson1().get(2).getState(), q3);
//                changeColor(progress1.getLesson1().get(3).getState(), q4);
//                changeColor(progress1.getLesson1().get(4).getState(), q5);
//                Log.d("COLORS1==", "onBind: "+progress1.getName());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().size());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(0).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(1).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(2).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(3).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson1().get(4).getAvailable());
//
//
//                break;
//            case R.id.lesson_two:
//                changeColor(progress1.getLesson2().get(0).getState(), q1);
//                changeColor(progress1.getLesson2().get(1).getState(), q2);
//                changeColor(progress1.getLesson2().get(2).getState(), q3);
//                changeColor(progress1.getLesson2().get(3).getState(), q4);
//                changeColor(progress1.getLesson2().get(4).getState(), q5);
//
//
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getQuestion());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getState());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(0).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(1).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(2).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(3).getAvailable());
//                Log.d("COLORS1==", "onBind: "+progress1.getLesson2().get(4).getAvailable());
//                break;

            case R.id.option_Lesson_One:

                    uncheckSelectors();
                    showHideQuestionsLayout();
                    dotSelectorOne.setColorFilter(context.getResources().getColor(R.color.main_yellow));
                    dotSelectorOne.setVisibility(View.VISIBLE);
                Log.d("TIMER", "onClick: "+progress1.getLesson1().size());
                Log.d("TIMER", "onClick: "+progress1.getLesson1().get(0).getState());
                changeColor(progress1.getLesson1().get(0).getState(), q1);
                changeColor(progress1.getLesson1().get(1).getState(), q2);
                changeColor(progress1.getLesson1().get(2).getState(), q3);
                changeColor(progress1.getLesson1().get(3).getState(), q4);
                changeColor(progress1.getLesson1().get(4).getState(), q5);
                currentLesson="L1";
                break;


            case R.id.option_Lesson_Two:

                uncheckSelectors();
                showHideQuestionsLayout();
                dotSelectorTwo.setColorFilter(context.getResources().getColor(R.color.main_yellow));
                dotSelectorTwo.setVisibility(View.VISIBLE);
                changeColor(progress1.getLesson2().get(0).getState(), q1);
                changeColor(progress1.getLesson2().get(1).getState(), q2);
                changeColor(progress1.getLesson2().get(2).getState(), q3);
                changeColor(progress1.getLesson2().get(3).getState(), q4);
                changeColor(progress1.getLesson2().get(4).getState(), q5);
                currentLesson="L2";
                break;

            case R.id.option_Lesson_Three:
                uncheckSelectors();
                showHideQuestionsLayout();
                dotSelectorThree.setColorFilter(context.getResources().getColor(R.color.main_yellow));
                dotSelectorThree.setVisibility(View.VISIBLE);
                //imageName+="Lesson3";
                break;


            case R.id.option_Lesson_Four:
                uncheckSelectors();
                showHideQuestionsLayout();
                dotSelectorFour.setColorFilter(context.getResources().getColor(R.color.main_yellow));
                dotSelectorFour.setVisibility(View.VISIBLE);
                break;


            case R.id.option_Lesson_Five:
                uncheckSelectors();
                showHideQuestionsLayout();
                dotSelectorFive.setColorFilter(context.getResources().getColor(R.color.main_yellow));
                dotSelectorFive.setVisibility(View.VISIBLE);
                break;

            case R.id.q1:
                currentQuestion="Q1";
                getImage(currentStudentName+currentLesson+currentQuestion);
                break;

            case R.id.q2:
                currentQuestion="Q2";
                getImage(currentStudentName+currentLesson+currentQuestion);
                break;

            case R.id.q3:
                currentQuestion="Q3";
                getImage(currentStudentName+currentLesson+currentQuestion);
                break;

            case R.id.q4:
                currentQuestion="Q4";
                getImage(currentStudentName+currentLesson+currentQuestion);
                break;

            case R.id.q5:
                currentQuestion="Q5";
                getImage(currentStudentName+currentLesson+currentQuestion);
                break;

        }


    }

    private void getImage(String imageName) {

        Log.d("IMAGENAME", "getImage: "+imageName);

        urlListener.sendUrl(imageName);

    }

    private void showHideQuestionsLayout() {
//        if (questionsView.getVisibility()==View.GONE){
//            questionsView.setVisibility(View.VISIBLE);
//        }else {
//            questionsView.setVisibility(View.GONE);
//            uncheckSelectors();
//        }


        questionsView.setVisibility(View.VISIBLE);


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