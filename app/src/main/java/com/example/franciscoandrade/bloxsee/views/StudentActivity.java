package com.example.franciscoandrade.bloxsee.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.StudentQuestionAdapter;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView studentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentName = (TextView) findViewById(R.id.student_name);
        setStudentName();
        setRecyclerView();


        //TODO: retrieve student name from DB and display in
        /*setting up the view to be the specific student's name
        * */

    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.student_questions_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Dummy Data for questions yet to come
        List<Questions> questionsList = new ArrayList<>();
        questionsList.add(new Questions("first"));
        questionsList.add(new Questions("Question two"));
        questionsList.add(new Questions("Question three"));

        recyclerView.setAdapter(new StudentQuestionAdapter(questionsList));

    }

    public void setStudentName() {
        Intent intent = getIntent();
        String studentUser = intent.getStringExtra("studentName");
        studentName.setText("Welcome, " + studentUser + "!");

    }


}
