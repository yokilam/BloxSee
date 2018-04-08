package com.example.franciscoandrade.bloxsee.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.StudentQuestionAdapter;
import com.example.franciscoandrade.bloxsee.controller.StudentQuestionAdapterLessonOne;
import com.example.franciscoandrade.bloxsee.model.Questions;
import com.example.franciscoandrade.bloxsee.model.Student;
import com.example.franciscoandrade.bloxsee.model.StudentInfo;
import com.example.franciscoandrade.bloxsee.model.StudentQuestions;
import com.example.franciscoandrade.bloxsee.views.teacher.TeacherMainPageActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOne;
    private TextView studentName;
    private ImageView bloxseeAvatar;
    private static final int NOTIFICATION_ID = 555;
    private static final String NOTIFICATION_CHANNEL = "C4Q Notifications";

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private StudentQuestions studentQuestions;
    private ChildEventListener childEventListener;
    private List <StudentInfo> questionsList;
    private List <StudentInfo> questionListOne;
    private String newQuestion;
    private String question;
    private String lesson;
    private StudentQuestionAdapter studentQuestionAdapter;
    private StudentQuestionAdapterLessonOne studentQuestionAdapterLessonOne;
    private TextView lessonOne, lessonTwo;
    private String name;
    private String lessonNum;
    private String lessonNumber;
    private String questionNum;
    private TextView signOutStudent;
    private Animation fromTop, fromBottom;
    private LinearLayout studentLayout;

    //this string will contain the curren name of the logged in user
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setUpViews();
        setStudentName();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        studentQuestions = new StudentQuestions();
        setRecyclerView();
        setUpNotification();
        signOut();
        fromTop = AnimationUtils.loadAnimation(this, R.anim.logo_from_top);
        fromBottom= AnimationUtils.loadAnimation(this, R.anim.logo_from_bottom);

    }

    private void setUpViews() {
        studentName = findViewById(R.id.student_name);
        signOutStudent = findViewById(R.id.signOutStudent);
        lessonOne= findViewById(R.id.student_lesson_one);
        lessonTwo= findViewById(R.id.student_lesson_two);
        bloxseeAvatar = findViewById(R.id.student_avatar);
        studentLayout= findViewById(R.id.student_layout);
    }

    private void signOut() {

        signOutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
        recyclerViewOne = findViewById(R.id.student_questions_rv2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerViewOne.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentQuestionAdapterLessonOne = new StudentQuestionAdapterLessonOne(this, user);
        studentQuestionAdapter = new StudentQuestionAdapter(this, user);
        recyclerView.setAdapter(studentQuestionAdapter);
        recyclerViewOne.setAdapter(studentQuestionAdapterLessonOne);

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
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("AVAILABLEB", "onChildAdded: " + dataSnapshot.getKey() + " - " + dataSnapshot.getValue());

                if (dataSnapshot.getKey().equals(user)) {
                    questionsList = new ArrayList <>();
                    questionListOne = new ArrayList <>();

                    for (int i = 1; i < 3; i++) {

                        for (int j = 0; j < 5; j++) {
                            lesson = "lesson" + i;

                            if (dataSnapshot.child(lesson).child(j + "").getValue(StudentQuestions.class) != null) {
                                studentQuestions = dataSnapshot.child(lesson).child(j + "").getValue(StudentQuestions.class);
                                question = studentQuestions.getQuestion();

                                Log.d("dataSS", "value" + String.valueOf(dataSnapshot.getValue()));
                                Log.d("dataSS", "key" + String.valueOf(dataSnapshot.getKey()));

                                Log.d("AVAILABLE", "onChildAdded: " + studentQuestions.getAvailable());
                                if (studentQuestions.getAvailable()) {
                                    StudentInfo sI = new StudentInfo();

                                    name = user;
//
                                    lessonNum ="L"+ String.valueOf(i);
                                    questionNum = "Q"+String.valueOf(j + 1);

                                    Log.d("QUESTION==", question + " lessonNum " + i + "questionNum: " + questionNum);

                                    sI.setLesson(lessonNum);
                                    sI.setQuestion(question);
                                    sI.setName(name);
                                    sI.setQuestionNum(questionNum);

                                    if (lessonNum.equals(String.valueOf(1))) {
                                        Log.d("lessonNum", lessonNum);
                                        questionListOne.add(sI);
                                    } else {
                                        questionsList.add(sI);
                                    }
                                    Log.d("QUESTONS==", "onChildAdded: " + lesson + " - " + question);

                                }
                            }
                        }
                    }
                    studentQuestionAdapter.notifyDataSetChanged();
                    studentQuestionAdapter.addQuestions(questionsList);
                    studentQuestionAdapter.notifyDataSetChanged();
                    studentQuestionAdapterLessonOne.notifyDataSetChanged();
                    studentQuestionAdapterLessonOne.addQuestions(questionListOne);
                    studentQuestionAdapterLessonOne.notifyDataSetChanged();

                    if (!questionsList.isEmpty()) {
//                        bloxseeAvatar.setAnimation(fromBottom);
//                        studentLayout.setAnimation(fromBottom);
//                        bloxseeAvatar.setVisibility(View.GONE);
                        lessonTwo.setVisibility(View.VISIBLE);
                        studentQuestionAdapter.notifyDataSetChanged();
                        studentQuestionAdapter.addQuestions(questionsList);
                        studentQuestionAdapter.notifyDataSetChanged();
                    } else if (!questionListOne.isEmpty()) {
//                        bloxseeAvatar.setAnimation(fromBottom);
//                        studentLayout.setAnimation(fromBottom);
//                        bloxseeAvatar.setVisibility(View.GONE);
                        lessonOne.setVisibility(View.VISIBLE);
                        studentQuestionAdapterLessonOne.notifyDataSetChanged();
                        studentQuestionAdapterLessonOne.addQuestions(questionListOne);
                        studentQuestionAdapterLessonOne.notifyDataSetChanged();
                    } else {
//                        bloxseeAvatar.setVisibility(View.VISIBLE);
//                        bloxseeAvatar.setAnimation(fromTop);
                        lessonOne.setVisibility(View.GONE);
                        lessonTwo.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals(user)) {
                    questionsList = new ArrayList <>();
                    questionListOne= new ArrayList <>();
                    for (int i = 1; i < 3; i++) {

                        for (int j = 0; j < 5; j++) {
                            lesson = "lesson" + i;

                            if (dataSnapshot.child(lesson).child(j + "").getValue(StudentQuestions.class) != null) {
                                studentQuestions = dataSnapshot.child(lesson).child(j + "").getValue(StudentQuestions.class);
                                question = studentQuestions.getQuestion();

                                Log.d("dataSS", "value" + String.valueOf(dataSnapshot.getValue()));
                                Log.d("dataSS", "key" + String.valueOf(dataSnapshot.getKey()));


                                Log.d("AVAILABLE", "onChildAdded: " + studentQuestions.getAvailable());
                                if (studentQuestions.getAvailable()) {
                                    StudentInfo sI = new StudentInfo();

                                    name = user;
                                    lessonNum = String.valueOf(i);
                                    questionNum = String.valueOf(j + 1);

                                    sI.setLesson(lessonNum);
                                    sI.setQuestion(question);
                                    sI.setName(name);
                                    sI.setQuestionNum(questionNum);

                                    if (lessonNum.equals(String.valueOf(1))) {
                                        Log.d("lessonNum", lessonNum);
                                        questionListOne.add(sI);
                                    } else {
                                        questionsList.add(sI);
                                    }
                                    Log.d("QUESTONS==", "onChildAdded: " + lesson + " - " + question);
                                }
                            }
                        }

                    }
                    studentQuestionAdapter.notifyDataSetChanged();
                    studentQuestionAdapter.addQuestions(questionsList);
                    studentQuestionAdapter.notifyDataSetChanged();
                    studentQuestionAdapterLessonOne.notifyDataSetChanged();
                    studentQuestionAdapterLessonOne.addQuestions(questionListOne);
                    studentQuestionAdapterLessonOne.notifyDataSetChanged();

                    if (!questionsList.isEmpty()) {
//                        bloxseeAvatar.setAnimation(fromBottom);
//                        studentLayout.setAnimation(fromBottom);
//                        bloxseeAvatar.setVisibility(View.GONE);
                        lessonTwo.setVisibility(View.VISIBLE);
                        studentQuestionAdapter.notifyDataSetChanged();
                        studentQuestionAdapter.addQuestions(questionsList);
                        studentQuestionAdapter.notifyDataSetChanged();
                    } else if (!questionListOne.isEmpty()) {
//                        bloxseeAvatar.setAnimation(fromBottom);
//                        studentLayout.setAnimation(fromBottom);
//                        bloxseeAvatar.setVisibility(View.GONE);
                        lessonOne.setVisibility(View.VISIBLE);
                        studentQuestionAdapterLessonOne.notifyDataSetChanged();
                        studentQuestionAdapterLessonOne.addQuestions(questionListOne);
                        studentQuestionAdapterLessonOne.notifyDataSetChanged();
                    } else {
//                        bloxseeAvatar.setVisibility(View.VISIBLE);
//                        bloxseeAvatar.setAnimation(fromTop);
                        lessonOne.setVisibility(View.GONE);
                        lessonTwo.setVisibility(View.GONE);
                    }

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

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Resume==", "onResume: im on resume");
        runLayoutAnimation(recyclerView);
        runLayoutAnimation(recyclerViewOne);
    }
}
