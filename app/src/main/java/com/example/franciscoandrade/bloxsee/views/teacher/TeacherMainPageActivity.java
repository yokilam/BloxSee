package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.teacher.PagerAdapterTeacher;

public class TeacherMainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_page);


        final ViewPager viewPager = findViewById(R.id.view_pager_teacher);
        viewPager.setAdapter(new PagerAdapterTeacher(getSupportFragmentManager()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_teacher);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.roster_teacher:
                        switchTeachFragments(R.id.fragment_container_teacher, new RosterFragment());
                        break;
                    case R.id.progress_teacher:
                        switchTeachFragments(R.id.fragment_container_teacher, new ProgressFragment());
                        break;
                    case R.id.questions_teacher:
                        switchTeachFragments(R.id.fragment_container_teacher, new QuestionsFragment());
                        break;
                }

                return true;
            }
        });

    }

    public void switchTeachFragments(int id, Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(id, fragment).commit();

    }

}
