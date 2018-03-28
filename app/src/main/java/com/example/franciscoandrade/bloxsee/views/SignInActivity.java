package com.example.franciscoandrade.bloxsee.views;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.example.franciscoandrade.bloxsee.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button teacher;
    private Button student;
    TextView exit;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    TeacherSignInFragment teacherSignInFragment;
    StudentSignInFragment studentSignInFragment;
    Animation fromBottom, fromTop;
    LottieAnimationView lottieAnimationView;
    LinearLayout mainContainer;
    ImageView bloxseeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //Intent intent = new Intent(this, BlocklyActivity.class);
        //startActivity(intent);

//      lottieAnimationView = findViewById(R.id.lottieAnimationView);
        mainContainer= findViewById(R.id.main_content);
        bloxseeIcon= findViewById(R.id.bloxsee_icon);
        mainContainer= findViewById(R.id.main_content);
        bloxseeIcon= findViewById(R.id.bloxsee_icon);

        setUpViews();

//        lottieLayout= findViewById(R.id.lottie_layout);
//        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                lottieAnimationView.clearAnimation();
//                lottieAnimationView.setVisibility(View.GONE);
//                bloxseeIcon.setVisibility(View.VISIBLE);
//                mainContainer.setBackgroundResource(R.drawable.bloxseebackground);
//                fromBottom = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.frombottom);
//                fromTop = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.fromtop);
//                setUpButtonAnimation();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });

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
        teacher = findViewById(R.id.teacherBtn);
        student = findViewById(R.id.studentBtn);
        exit = findViewById(R.id.exit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
