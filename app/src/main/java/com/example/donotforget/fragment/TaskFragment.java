package com.example.donotforget.fragment;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.donotforget.adapter.CurrentTasksAdapter;
import com.example.donotforget.model.Item;
import com.example.donotforget.model.ModelTask;

import java.util.List;

//создаем абстрактный класс для обьединени DonetaskAdapter & TabAdapter

public abstract class TaskFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CurrentTasksAdapter adapter;

    public void addTask(ModelTask newTask) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);

                //добавляем таски отсортированными по дате.
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;//цикл прерывается при нахождении первого элемента с бОльшей датой.
                }
            }
        }

        if (position != -1) {
            adapter.addItem(position, newTask);
        }else{
            adapter.addItem(newTask);
        }
    }

}
