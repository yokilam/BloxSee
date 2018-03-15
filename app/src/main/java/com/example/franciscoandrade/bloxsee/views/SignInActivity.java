package com.example.franciscoandrade.bloxsee.views;

import android.app.FragmentManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.franciscoandrade.bloxsee.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button teacher;
    private Button student;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setUpViews();

        teacher.setOnClickListener(this);
        student.setOnClickListener(this);
    }

    public void setUpViews() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        teacher= findViewById(R.id.teacherBtn);
        student= findViewById(R.id.studentBtn);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.teacherBtn: {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new TeacherSignInFragment())
                        .commit();
                break;
            }
            case R.id.studentBtn: {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                android.support.v4.app.FragmentManager fragmentManager1= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1= fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.main_container, new StudentSignInFragment())
                        .commit();
                break;
            }
        }

    }
}
