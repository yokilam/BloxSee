package com.example.franciscoandrade.bloxsee.controller.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.franciscoandrade.bloxsee.views.teacher.ProgressFragment;
import com.example.franciscoandrade.bloxsee.views.teacher.QuestionsFragment;
import com.example.franciscoandrade.bloxsee.views.teacher.RosterFragment;

/**
 * Created by joannesong on 3/15/18.
 */

public class PagerAdapterTeacher extends FragmentStatePagerAdapter{

    public PagerAdapterTeacher(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RosterFragment();
                case 1:
                    return new ProgressFragment();
                case 2:
                    return new QuestionsFragment();
                default:
                    return null;
            }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
