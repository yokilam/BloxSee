package com.example.franciscoandrade.bloxsee.views.teacher;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                Log.d("clicked", "IT IS WORKING");
                switch(item.getItemId()){
                    case R.id.roster_teacher:
                        switchTeachFragments(new RosterFragment());
                        break;
                    case R.id.progress_teacher:
                        switchTeachFragments(new ProgressFragment());
                        break;
                    case R.id.questions_teacher:
                        switchTeachFragments(new QuestionsFragment());
                        break;
                }
                return true;
            }
        });

    }

    public void switchTeachFragments(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container_teacher, fragment).commit();

    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        callsFragment=new CallsFragment();
//        chatFragment=new ChatFragment();
//        contactsFragment=new ContactsFragment();
//        adapter.addFragment(callsFragment);
//        adapter.addFragment(chatFragment);
//        adapter.addFragment(contactsFragment);
//        viewPager.setAdapter(adapter);
//    }

}
