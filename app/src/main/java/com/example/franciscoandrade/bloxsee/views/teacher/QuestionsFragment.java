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
    CheckBox questionOne, questionTwo, questionThree, questionFour, questionFive;
    List<Boolean> isQuestionAvailable;
    List<Boolean> listChckbox;

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
        send.setOnClickListener(this);
        listChckbox= new ArrayList<>();
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

                    listChckbox.set(0, q1);
                    listChckbox.set(1, q2);
                    listChckbox.set(2, q3);
                    listChckbox.set(3, q4);
                    listChckbox.set(4, q5);

                    questionOne.setChecked(listChckbox.get(0));
                    questionTwo.setChecked(listChckbox.get(1));
                    questionThree.setChecked(listChckbox.get(2));
                    questionFour.setChecked(listChckbox.get(3));
                    questionFive.setChecked(listChckbox.get(4));

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
        ChildEventListener childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listChckbox.set(0,questionOne.isChecked());
                listChckbox.set(1,questionTwo.isChecked());
                listChckbox.set(2,questionThree.isChecked());
                listChckbox.set(3, questionFour.isChecked());
                listChckbox.set(4, questionFive.isChecked());

                for (int i = 1; i < 6; i++) {
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i)+"").child("available").setValue(listChckbox.get((i-1)));
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i)+"").child("question").setValue("Question1Test");
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson1").child((i)+"").child("state").setValue("null");
                    ref.child("students").child(dataSnapshot.getKey()).child("lesson2").child((i)+"").child("available").setValue(listChckbox.get((i-1)));

                }
                questionOne.setChecked(listChckbox.get(0));
                questionTwo.setChecked(listChckbox.get(1));
                questionThree.setChecked(listChckbox.get(2));
                questionFour.setChecked(listChckbox.get(3));
                questionFive.setChecked(listChckbox.get(4));
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

}
