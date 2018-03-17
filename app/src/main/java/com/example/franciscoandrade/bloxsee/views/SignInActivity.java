package com.example.franciscoandrade.bloxsee.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.views.student.BlocklyActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button teacher;
    private Button student;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        Intent intent = new Intent(this, BlocklyActivity.class);
//        startActivity(intent);

        setUpViews();

        teacher.setOnClickListener(this);
        student.setOnClickListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpViews() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        teacher = findViewById(R.id.teacherBtn);
        student = findViewById(R.id.studentBtn);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        setBottomSheetBehavior();
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
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        switch (v.getId()) {
            // TODO: Fragments are being added to stack, make fragments not to be added to stack
            case R.id.teacherBtn: {
                fragmentManager.beginTransaction().replace(R.id.loginContainer, new TeacherSignInFragment()).addToBackStack("backToActivity").addToBackStack(null).commit();
                break;
            }
            case R.id.studentBtn: {
                fragmentManager.beginTransaction().replace(R.id.loginContainer, new StudentSignInFragment()).addToBackStack("backToActivity").addToBackStack(null).commit();
                break;
            }
        }
    }
}
