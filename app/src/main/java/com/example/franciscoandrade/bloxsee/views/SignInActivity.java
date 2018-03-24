package com.example.franciscoandrade.bloxsee.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.views.student.BlocklyActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button teacher;
    private Button student;
    private BottomSheetBehavior mBottomSheetBehavior;
    private FrameLayout loginContainer;
    TextView exit;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    TeacherSignInFragment teacherSignInFragment;
    StudentSignInFragment studentSignInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        Intent intent = new Intent(this, BlocklyActivity.class);
//        startActivity(intent);

        setUpViews();

        teacher.setOnClickListener(this);
        student.setOnClickListener(this);


        teacherSignInFragment= new TeacherSignInFragment();
        studentSignInFragment= new StudentSignInFragment();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (teacherSignInFragment.isVisible()) {
                    Toast.makeText(SignInActivity.this, "X clicked", Toast.LENGTH_SHORT).show();
                    fragmentManager.beginTransaction().remove(teacherSignInFragment).commit();
                }
                if (studentSignInFragment.isVisible()){
                    fragmentManager.beginTransaction().remove(studentSignInFragment).commit();
                }

                exit.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpViews() {
        //View bottomSheet = findViewById(R.id.bottom_sheet);
        teacher = findViewById(R.id.teacherBtn);
        student = findViewById(R.id.studentBtn);
        exit = findViewById(R.id.exit);
       // mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        //setBottomSheetBehavior();
    }

    private void setBottomSheetBehavior() {
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetBehavior.setPeekHeight(70);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    public void onClick(View v) {
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        switch (v.getId()) {
            // TODO: Fragments are being added to stack, make fragments not to be added to stack
            case R.id.teacherBtn: {
                exit.setBackgroundColor(Color.parseColor("#FF6699"));
                exit.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.loginContainer, teacherSignInFragment).addToBackStack("backToActivity").addToBackStack(null).commit();
                break;
            }
            case R.id.studentBtn: {
                exit.setBackgroundColor(Color.parseColor("#6699CC"));
                exit.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.loginContainer, studentSignInFragment).addToBackStack("backToActivity").addToBackStack(null).commit();
                break;
            }
        }
    }
}
