package com.example.franciscoandrade.bloxsee.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.StudentQuestionAdapter;
import com.example.franciscoandrade.bloxsee.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView studentAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setRecyclerView();

        //TODO: set listeners for problems to BloxSee student workspace activity

    }
    public void setRecyclerView(){
        recyclerView = findViewById(R.id.student_questions_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Questions> questionsList= new ArrayList<>();
        questionsList.add(new Questions("first"));
        questionsList.add(new Questions("Question two"));
        questionsList.add(new Questions("Question three"));

        recyclerView.setAdapter(new StudentQuestionAdapter(questionsList));

    }


}
