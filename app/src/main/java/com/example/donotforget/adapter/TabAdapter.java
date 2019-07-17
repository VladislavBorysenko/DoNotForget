package com.example.donotforget.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.donotforget.fragment.CurrentTaskFragment;
import com.example.donotforget.fragment.DoneTaskFragment;


public class TabAdapter extends FragmentStatePagerAdapter {
    //Адаптер - это посредник между данными и их представлением.

    private int numberOfTabs;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    /**
     * @param fm
     * @deprecated
     */
    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        //FragmentManager управляет фрагментами на вкладках.
        super(fm);
        this.numberOfTabs = numberOfTabs;
        currentTaskFragment = new CurrentTaskFragment();
        doneTaskFragment = new DoneTaskFragment();
    }

    @Override
    public Fragment getItem(int i) {
        //при каждом вызове метода getItem(int i) мы не создаём фрагменты,
        //а возвращаем уже существующие (currentTaskFragment, doneTaskFragment)

        switch (i) {
            case 0:
                return currentTaskFragment;

            case 1:
                return doneTaskFragment;

            default:
                return null;
        }
    }


    //Метод возвращает количество вкладок.
    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
