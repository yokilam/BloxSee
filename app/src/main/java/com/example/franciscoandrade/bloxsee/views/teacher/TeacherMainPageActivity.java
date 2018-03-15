package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.franciscoandrade.bloxsee.R;
import com.example.franciscoandrade.bloxsee.controller.teacher.PagerAdapterTeacher;

public class TeacherMainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_page);


        final ViewPager viewPager = findViewById(R.id.view_pager_teacher);
        viewPager.setAdapter(new PagerAdapterTeacher(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tab_layout_teacher);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Roster");
        tabLayout.getTabAt(1).setText("Progress");
        tabLayout.getTabAt(2).setText("Questions");

        //PagerAdapterTeacher pagerAdapterTeacher = new PagerAdapterTeacher(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
