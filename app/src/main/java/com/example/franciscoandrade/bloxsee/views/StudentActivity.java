package com.example.franciscoandrade.bloxsee.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
import com.example.franciscoandrade.bloxsee.model.StudentInfo;
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
    private ImageView bloxseeAvatar;
    private static final int NOTIFICATION_ID = 555;
    private static final String NOTIFICATION_CHANNEL = "C4Q Notifications";

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StudentQuestions studentQuestions;
    private ChildEventListener childEventListener;
    private List<StudentInfo> questionsList;
    private String question;
    private String lesson;
    private StudentQuestionAdapter studentQuestionAdapter;

    String name;
    String lessonNum;
    String questionNum;

    //this string will contain the curren name of the logged in user
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        studentName = findViewById(R.id.student_name);
        setStudentName();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        studentQuestions= new StudentQuestions();
        setRecyclerView();
        setUpNotification();
        bloxseeAvatar = (ImageView) findViewById(R.id.student_avatar);


    }

    private void setUpNotification() {
        // Define an intent to trigger when notification is selected (in this case to open an activity)
        Intent intent = new Intent(this, StudentActivity.class);

        // Turn this into a PendingIntent
        int requestID = (int) System.currentTimeMillis(); // Unique requestID to differentiate between various notification with same notification ID
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // Cancel old intent and create new one
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, flags);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle("New Question Added!")
                .setContentText("A new question is posted!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.student_questions_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        studentQuestionAdapter = new StudentQuestionAdapter(this, user);
        recyclerView.setAdapter(studentQuestionAdapter);

    }

    public void setStudentName() {
        Intent intent = getIntent();
        user = intent.getStringExtra("studentName");
        studentName.setText("Welcome, " + user + "!");

    }


    //When app starts it connects to DB listens for all users and filters questions that are set for user to see
    //this questions are shown in the student recycler view
    @Override
    protected void onStart() {
        super.onStart();
         childEventListener= new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Log.d("AVAILABLEB", "onChildAdded: "+dataSnapshot.getKey()+" - "+dataSnapshot.getValue());

                 if (dataSnapshot.getKey().equals(user) ) {
                     questionsList = new ArrayList<>();
                  for (int i = 1; i < 3; i++) {

                     for (int j = 0; j < 5; j++) {
                         lesson = "lesson" + i;

                         if(dataSnapshot.child(lesson).child( j+"").getValue(StudentQuestions.class)!=null) {
                             studentQuestions = dataSnapshot.child(lesson).child(j + "").getValue(StudentQuestions.class);
                             question = studentQuestions.getQuestion() + " - " + lesson;

                             Log.d("dataSS", "value" + String.valueOf(dataSnapshot.getValue()));
                             Log.d("dataSS", "key" + String.valueOf(dataSnapshot.getKey()));


                             Log.d("AVAILABLE", "onChildAdded: "+ studentQuestions.getAvailable());
                             if (studentQuestions.getAvailable()) {
                                 StudentInfo sI = new StudentInfo();

                                 name = user;
                                 lessonNum = "L" + String.valueOf(i);
                                 questionNum = "Q" + String.valueOf(j+1);

                                 sI.setLesson(lessonNum);
                                 sI.setQuestion(question);
                                 sI.setName(name);
                                 sI.setQuestionNum(questionNum);

                                 Log.d("QUESTONS==", "onChildAdded: " + lesson + " - " + question);
                                 questionsList.add(sI);
                             }
                         }
                     }

                 }
                     studentQuestionAdapter.notifyDataSetChanged();
                     studentQuestionAdapter.addQuestions(questionsList);
                     studentQuestionAdapter.notifyDataSetChanged();
                     if(!questionsList.isEmpty()){
                        studentQuestionAdapter.notifyDataSetChanged();
                        studentQuestionAdapter.addQuestions(questionsList);
                         studentQuestionAdapter.notifyDataSetChanged();
                     }

                 }
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                 if (dataSnapshot.getKey().equals(user) ) {
                     // studentQuestions= dataSnapshot.child("lesson1").child("1").getValue(StudentQuestions.class);
                     questionsList= new ArrayList<>();
                     for (int i = 1; i < 3; i++) {

                         for (int j = 1; j < 6; j++) {
                             lesson= "lesson"+i;
                             studentQuestions = dataSnapshot.child(lesson).child(j+"").getValue(StudentQuestions.class);
                             question =studentQuestions.getQuestion()+" - "+lesson;
                             if(studentQuestions.getAvailable()){
                                 Log.d("QUESTONS==", "onChildAdded: "+lesson+" - "+question);
                                 //questionsList.add(question);
                             }
                         }

                     }
                     studentQuestionAdapter.notifyDataSetChanged();
                     studentQuestionAdapter.addQuestions(questionsList);

                     setUpNotification();
                 }
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

    @Override
    protected void onStop() {
        super.onStop();
        ref.child("students").removeEventListener(childEventListener);
    }
}
