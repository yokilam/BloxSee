package com.example.franciscoandrade.bloxsee.views.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.model.Level;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
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
    HashMap<String, String> lista;
    CheckBox questionOne, questionTwo, questionThree, questionFour, questionFive;
    StudentQuestions studentQuestions1;
    StudentQuestions studentQuestions2;
    StudentQuestions studentQuestions3;
    StudentQuestions studentQuestions4;
    StudentQuestions studentQuestions5;
    List<StudentQuestions>listStudentQuestions;
    List<Boolean> isQuestionAvailable;
    List<CheckBox> lessonOneCheckBox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_questions, container, false);
        Log.d(TAG, "onCreateView: I am on question fragment");
        questionOne= view.findViewById(R.id.questionOne);
        questionTwo= view.findViewById(R.id.questionTwo);
        questionThree= view.findViewById(R.id.questionThree);
        questionFour= view.findViewById(R.id.questionFour);
        questionFive= view.findViewById(R.id.questionFive);
        lessonOneCheckBox= new ArrayList <>();
        lessonOneCheckBox.add(questionOne);
        lessonOneCheckBox.add(questionTwo);
        lessonOneCheckBox.add(questionThree);
        lessonOneCheckBox.add(questionFour);
        lessonOneCheckBox.add(questionFive);


        setUpViews(view);
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
        lista= new HashMap<String, String>();
        studentQuestions1= new StudentQuestions(questionOne.isChecked(), "passed", "this is q1");
        studentQuestions2= new StudentQuestions(questionTwo.isChecked(), "failed", "this is q2");
        studentQuestions3= new StudentQuestions(questionThree.isChecked(), "failed", "this is q3");
        studentQuestions4= new StudentQuestions(questionFour.isChecked(), "failed", "this is q4");
        studentQuestions5= new StudentQuestions(questionFive.isChecked(), "passed", "this is q5");
        listStudentQuestions= new ArrayList<>();
        listStudentQuestions.add(studentQuestions1);
        listStudentQuestions.add(studentQuestions2);
        listStudentQuestions.add(studentQuestions3);
        listStudentQuestions.add(studentQuestions4);
        listStudentQuestions.add(studentQuestions5);

        ChildEventListener childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                setUpCheckBoxes(dataSnapshot);
                Log.d("LISTA===", "child==: "+dataSnapshot.getValue());
                Log.d("LISTA===", "child==: "+dataSnapshot.getKey());
                for (int i = 0; i < 5; i++) {

                    for (int j = 1; j <6 ; j++) {
                        ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i+1)+"").setValue(listStudentQuestions.get(i));
                        ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((i+1)+"").setValue(listStudentQuestions.get(i));
//                        Log.d("AVAILABLE===", ""+ dataSnapshot.child("lesson1").child((i+1)+"").child("available").getValue());

                    }

                }


//                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child("Lesson1").child("1").setValue(studentQuestions1);
//                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child("Lesson2").child("2").setValue(studentQuestions1);
//                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child("Lesson3").child("3").setValue(studentQuestions1);
//                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child("Lesson4").child("4").setValue(studentQuestions1);
//                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child("Lesson5").child("5").setValue(studentQuestions1);
//
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

    private void setUpCheckBoxes(DataSnapshot dataSnapshot) {
        //getting lesson's question availability and add it to a list
        for (int k = 0; k < 5; k++){
            Log.d("AVAILABLE===", ""+ dataSnapshot.child("lesson1").child((k+1)+"").child("available").getValue().toString());
            Boolean isAvailable= Boolean.valueOf(dataSnapshot.child("lesson1").child((k+1)+"").child("available").getValue().toString());
            Log.d("Available2=== ", ""+isAvailable);
            isQuestionAvailable.add(isAvailable);
        }

        //run the availability list and set the checkboxes
        for (int h=0; h < 5; h++) {
            if (isQuestionAvailable.get(h)) {
                lessonOneCheckBox.get(h).setChecked(true);
            } else {
                lessonOneCheckBox.get(h).setChecked(false);
            }
        }
    }

    private void setUpViews(View view) {
        send = view.findViewById(R.id.send_button);
        recyclerView= view.findViewById(R.id.question_recyclerview);


        setClickCpature();
    }

    private void setClickCpature() {
        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        getStudents();

    }
}
