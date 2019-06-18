package com.example.donotforget.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.donotforget.fragment.CurrentTaskFragment;
import com.example.donotforget.fragment.DoneTaskFragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

  /*  public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;*/

   /* private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;*/

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;

    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new CurrentTaskFragment();
            case 1:
                return new DoneTaskFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
