package com.example.franciscoandrade.bloxsee.controller.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.franciscoandrade.bloxsee.views.teacher.ProgressFragment;
import com.example.franciscoandrade.bloxsee.views.teacher.QuestionsFragment;
import com.example.franciscoandrade.bloxsee.views.teacher.RosterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joannesong on 3/15/18.
 */

public class PagerAdapterTeacher extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public PagerAdapterTeacher(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
