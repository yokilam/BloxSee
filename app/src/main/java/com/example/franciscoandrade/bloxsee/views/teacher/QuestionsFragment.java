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

    private List<Level> levelList= new ArrayList <>();
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


        setUpViews(view);

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
        lista= new HashMap<String, String>();
        studentQuestions1= new StudentQuestions();
        studentQuestions1.setQ1(questionOne.isChecked());
        studentQuestions1.setQ2(questionTwo.isChecked());
        studentQuestions1.setQ3(questionThree.isChecked());
        studentQuestions1.setQ4(questionFour.isChecked());
        studentQuestions1.setQ5(questionFive.isChecked());
        ChildEventListener childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("LISTA===", "child==: "+dataSnapshot.getValue());
                Log.d("LISTA===", "child==: "+dataSnapshot.getKey());
                ref.child("students").child(dataSnapshot.getKey()).child("lesson1").setValue(studentQuestions1);


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
