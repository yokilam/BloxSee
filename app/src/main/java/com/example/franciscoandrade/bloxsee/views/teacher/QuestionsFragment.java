package com.example.franciscoandrade.bloxsee.views.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Level;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
import com.example.franciscoandrade.bloxsee.util.ExpandableLayoutAnimation;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class QuestionsFragment extends Fragment implements View.OnClickListener{

    private List<Level> levelList;
    private RecyclerView recyclerView;
    private Button send;

    private DatabaseReference ref;
    private FirebaseDatabase database;
    List<String> listStudents;
    Student studentModel;
    StudentQuestions studentQuestions;
    CheckBox questionOne, questionTwo, questionThree, questionFour, questionFive;
    CheckBox questionOneLessonTwo, questionTwoLessonTwo, questionThreeLessonTwo, questionFourLessonTwo, questionFiveLessonTwo;
    List<Boolean> isQuestionAvailable;
    List<Boolean> listChckbox;
    ExpandableLinearLayout question_expandable_layout, Two;
    ExpandableLayoutAnimation expandableLayoutAnimation;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    LinearLayout level, levelTwo;
    TextView  lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10;
    ExpandableLinearLayout lesson3Expand, lesson4Expand, lesson5Expand, lesson6Expand, lesson7Expand, lesson8Expand, lesson9Expand, lesson10Expand;

    ExpandIconView expand1, expand2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_questions, container, false);
        setUpViews(view);
        Log.d(TAG, "onCreateView: I am on question fragment");
        questionOne= view.findViewById(R.id.questionOne);
        questionTwo= view.findViewById(R.id.questionTwo);
        questionThree= view.findViewById(R.id.questionThree);
        questionFour= view.findViewById(R.id.questionFour);
        questionFive= view.findViewById(R.id.questionFive);
        questionOneLessonTwo= view.findViewById(R.id.questionOneLessonTwo);
        questionTwoLessonTwo= view.findViewById(R.id.questionTwoLessonTwo);
        questionThreeLessonTwo= view.findViewById(R.id.questionThreeLessonTwo);
        questionFourLessonTwo= view.findViewById(R.id.questionFourLessonTwo);
        questionFiveLessonTwo= view.findViewById(R.id.questionFiveLessonTwo);
        question_expandable_layout= view.findViewById(R.id.question_expandable_layout);
        levelTwo= view.findViewById(R.id.levelTwo);
        level= view.findViewById(R.id.level);
        Two= view.findViewById(R.id.Two);
        lesson3= view.findViewById(R.id.lesson3);
        lesson4= view.findViewById(R.id.lesson4);
        lesson5= view.findViewById(R.id.lesson5);
        lesson6= view.findViewById(R.id.lesson6);
        lesson7= view.findViewById(R.id.lesson7);
        lesson8= view.findViewById(R.id.lesson8);
        lesson9= view.findViewById(R.id.lesson9);
        lesson10= view.findViewById(R.id.lesson10);
        expandableLayoutAnimation= new ExpandableLayoutAnimation();
//        lesson3Expand= new ExpandableLayoutAnimation();
//        lesson4Expand= new ExpandableLayoutAnimation();
//        lesson5Expand= new ExpandableLayoutAnimation();
//        lesson6Expand= new ExpandableLayoutAnimation();
//        lesson7Expand= new ExpandableLayoutAnimation();
//        lesson8Expand= new ExpandableLayoutAnimation();
//        lesson9Expand= new ExpandableLayoutAnimation();
//        lesson10Expand= new ExpandableLayoutAnimation();
        lesson3Expand= view.findViewById(R.id.lesson3Expand);
        lesson4Expand= view.findViewById(R.id.lesson4Expand);
        lesson5Expand= view.findViewById(R.id.lesson5Expand);
        lesson6Expand= view.findViewById(R.id.lesson6Expand);
        lesson7Expand= view.findViewById(R.id.lesson7Expand);
        lesson8Expand= view.findViewById(R.id.lesson8Expand);
        lesson9Expand= view.findViewById(R.id.lesson9Expand);
        lesson10Expand=view.findViewById(R.id.lesson10Expand);
        lesson10Expand.collapse();
        expand1=view.findViewById(R.id.expand1);
        expand2=view.findViewById(R.id.expand2);

        send.setOnClickListener(this);
        question_expandable_layout.setOnClickListener(this);
        Two.setOnClickListener(this);
        levelTwo.setOnClickListener(this);
        level.setOnClickListener(this);
        lesson3.setOnClickListener(this);
        lesson4.setOnClickListener(this);
        lesson5.setOnClickListener(this);
        lesson6.setOnClickListener(this);
        lesson7.setOnClickListener(this);
        lesson8.setOnClickListener(this);
        lesson9.setOnClickListener(this);
        lesson10.setOnClickListener(this);
        listChckbox= new ArrayList<>();
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        listChckbox.add(true);
        levelList= new ArrayList <>();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        listStudents= new ArrayList<>();
        getStudents();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        levelList.add(new Level("Level One", "one", "two", "three", "four", "five"));
        levelList.add(new Level("Level Two", "one", "two", "three", "four", "five"));
        levelList.add(new Level("Level Three", "one", "two", "three", "four", "five"));

        recyclerView.setAdapter(new TeacherQuestionAdapter(levelList));

        return view;
    }

    private void getStudents() {
        studentModel= new Student();
        studentQuestions= new StudentQuestions();
        isQuestionAvailable= new ArrayList <>();

        ChildEventListener childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("lesson1").child("1").child("available").getValue()!=null) {
                    Log.d("CURRENTUSER=", "onChildAdded: " + dataSnapshot.getKey());
                    Boolean q1 = Boolean.parseBoolean(dataSnapshot.child("lesson1").child("1").child("available").getValue().toString());
                    Boolean q2 = Boolean.parseBoolean(dataSnapshot.child("lesson1").child("2").child("available").getValue().toString());
                    Boolean q3 = Boolean.parseBoolean(dataSnapshot.child("lesson1").child("3").child("available").getValue().toString());
                    Boolean q4 = Boolean.parseBoolean(dataSnapshot.child("lesson1").child("4").child("available").getValue().toString());
                    Boolean q5 = Boolean.parseBoolean(dataSnapshot.child("lesson1").child("5").child("available").getValue().toString());

                    Boolean q1L2= Boolean.parseBoolean(dataSnapshot.child("lesson2").child("1").child("available").getValue().toString());
                    Boolean q2L2= Boolean.parseBoolean(dataSnapshot.child("lesson2").child("2").child("available").getValue().toString());
                    Boolean q3L2= Boolean.parseBoolean(dataSnapshot.child("lesson2").child("3").child("available").getValue().toString());
                    Boolean q4L2= Boolean.parseBoolean(dataSnapshot.child("lesson2").child("4").child("available").getValue().toString());
                    Boolean q5L2= Boolean.parseBoolean(dataSnapshot.child("lesson2").child("5").child("available").getValue().toString());


                    listChckbox.set(0, q1);
                    listChckbox.set(1, q2);
                    listChckbox.set(2, q3);
                    listChckbox.set(3, q4);
                    listChckbox.set(4, q5);

                    listChckbox.set(5, q1L2);
                    listChckbox.set(6, q2L2);
                    listChckbox.set(7, q3L2);
                    listChckbox.set(8, q4L2);
                    listChckbox.set(9, q5L2);


                    questionOne.setChecked(listChckbox.get(0));
                    questionTwo.setChecked(listChckbox.get(1));
                    questionThree.setChecked(listChckbox.get(2));
                    questionFour.setChecked(listChckbox.get(3));
                    questionFive.setChecked(listChckbox.get(4));

                    questionOneLessonTwo.setChecked(listChckbox.get(5));
                    questionTwoLessonTwo.setChecked(listChckbox.get(6));
                    questionThreeLessonTwo.setChecked(listChckbox.get(7));
                    questionFourLessonTwo.setChecked(listChckbox.get(8));
                    questionFiveLessonTwo.setChecked(listChckbox.get(9));

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.child("students").addChildEventListener(childEventListener);

    }


    private void setUpViews(View view) {
        send = view.findViewById(R.id.send_button);
        recyclerView= view.findViewById(R.id.question_recyclerview);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.send_button:
                Log.d("CLICK==", "onClick: ==Button");

                    sendQuestions();
                break;

            case R.id.level:
                Log.d("CLICK==", "onClick: ==level1");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, question_expandable_layout, R.color.white, expandState);

                expand1.switchState();

                break;

            case R.id.levelTwo:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, Two, R.color.white, expandState);
                expand2.switchState();
                break;



            case R.id.lesson3:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson3Expand, R.color.white, expandState);
                break;

            case R.id.lesson4:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson4Expand, R.color.white, expandState);
                break;

            case R.id.lesson5:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson5Expand, R.color.white, expandState);
                break;


            case R.id.lesson6:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson6Expand, R.color.white, expandState);
                break;


            case R.id.lesson7:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson7Expand, R.color.white, expandState);
                break;


            case R.id.lesson8:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson8Expand, R.color.white, expandState);
                break;

            case R.id.lesson9:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson9Expand, R.color.white, expandState);
                break;

            case R.id.lesson10:
                Log.d("CLICK==", "onClick: ==level2");
                expandableLayoutAnimation.changeExpandableLayoutColorAndAnimation
                        (v, lesson10Expand, R.color.white, expandState);
                break;

        }


    }

    private void sendQuestions() {

        ChildEventListener childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listChckbox.set(0,questionOne.isChecked());
                listChckbox.set(1,questionTwo.isChecked());
                listChckbox.set(2,questionThree.isChecked());
                listChckbox.set(3, questionFour.isChecked());
                listChckbox.set(4, questionFive.isChecked());

                listChckbox.set(5,questionOneLessonTwo.isChecked());
                listChckbox.set(6,questionTwoLessonTwo.isChecked());
                listChckbox.set(7,questionThreeLessonTwo.isChecked());
                listChckbox.set(8, questionFourLessonTwo.isChecked());
                listChckbox.set(9, questionFiveLessonTwo.isChecked());

                for (int i = 1; i < 6; i++) {
                    setQuestions(i, dataSnapshot);
                    //
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i)+"").child("available").setValue(listChckbox.get((i-1)));
                    //ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i)+"").child("question").setValue("Question: "+i);

                    //
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((i)+"").child("available").setValue(listChckbox.get((i+4)));
                    //ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((i)+"").child("question").setValue("Question: "+i);


                    if(dataSnapshot.child("lesson1").child(i+"").child("state").getValue() == null){
                        ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child(i+"").child("state").setValue("null");
                    }
                    if(dataSnapshot.child("lesson2").child(i+"").child("state").getValue() == null){
                        ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child(i+"").child("state").setValue("null");
                    }



                }
                questionOne.setChecked(listChckbox.get(0));
                questionTwo.setChecked(listChckbox.get(1));
                questionThree.setChecked(listChckbox.get(2));
                questionFour.setChecked(listChckbox.get(3));
                questionFive.setChecked(listChckbox.get(4));
                questionOneLessonTwo.setChecked(listChckbox.get(5));
                questionTwoLessonTwo.setChecked(listChckbox.get(6));
                questionThreeLessonTwo.setChecked(listChckbox.get(7));
                questionFourLessonTwo.setChecked(listChckbox.get(8));
                questionFiveLessonTwo.setChecked(listChckbox.get(9));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.child("students").addChildEventListener(childEventListener);

    }


    private void setQuestions(int num, DataSnapshot dataSnapshot) {
        switch (num){
            case 1:
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("question").setValue(num+". Move the sprite to the right");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("answer").setValue("start\\n” + “moveright\\n” + “movedown\\n");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((num)+"").child("question").setValue(num+ ". Add a red color to the street light. ");
                break;

            case 2:
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("question").setValue(num+". Move the sprite to the center ");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((num)+"").child("question").setValue(num+ ". Add a yellow color to the street light. ");
                break;

            case 3:
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("question").setValue(num+". Make your sprite move from one edge to the other edge. Repeat this motion twice.");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((num)+"").child("question").setValue(num+ ". Add a green color to the street light. ");
                break;

            case 4:
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("question").setValue(num+ ". Change the backdrop");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((num)+"").child("question").setValue(num+ ". Flash the yellow light 5 times.");
                break;

            case 5:
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((num)+"").child("question").setValue(num+ ". Delete the penguin sprite. Create a new sprite.");
                ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((num)+"").child("question").setValue(num+ ". Flash the yellow light 3 times. Then flash the green light 3 times");
                break;
        }
    }

}
