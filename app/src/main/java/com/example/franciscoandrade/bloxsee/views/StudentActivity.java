package com.example.franciscoandrade.bloxsee.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.StudentQuestionAdapter;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView studentName;


    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StudentQuestions studentQuestions;
    private ChildEventListener childEventListener;
    List<String> questionsList;
    String question;
    String lesson;
    StudentQuestionAdapter studentQuestionAdapter;

    //this string will contain the curren name of the logged in user
    String user= "isco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        studentQuestions= new StudentQuestions();
        setRecyclerView();




    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.student_questions_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);





        studentQuestionAdapter= new StudentQuestionAdapter(this);
        recyclerView.setAdapter(studentQuestionAdapter);

    }

    public void setStudentName() {
        Intent intent = getIntent();
        String studentUser = intent.getStringExtra("studentName");
        studentName.setText("Welcome, " + studentUser + "!");

    }


    //When app starts it connects to DB listens for all users and filters questions that are set for user to see
    //this questions are shown in the student recycler view
    @Override
    protected void onStart() {
        super.onStart();
         childEventListener= new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                 if (dataSnapshot.getKey().equals(user) ) {
                // studentQuestions= dataSnapshot.child("lesson1").child("1").getValue(StudentQuestions.class);
                     questionsList= new ArrayList<>();
                  for (int i = 1; i < 3; i++) {

                     for (int j = 1; j < 6; j++) {
                         lesson= "lesson"+i;
                         studentQuestions= dataSnapshot.child(lesson).child(j+"").getValue(StudentQuestions.class);
                         question=studentQuestions.getQuestion()+" - "+lesson;
                         if(studentQuestions.getAvailable()){
                             Log.d("QUESTONS==", "onChildAdded: "+lesson+" - "+question);
                             questionsList.add(question);
                         }
                     }

                 }
                     studentQuestionAdapter.addQuestions(questionsList);

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
}
