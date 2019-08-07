package com.example.donotforget.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.donotforget.R;
import com.example.donotforget.adapter.DoneTaskAdapter;
import com.example.donotforget.database.DBHelper;
import com.example.donotforget.model.ModelTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {


    public DoneTaskFragment() {
        // Required empty public constructor
    }

    OnTaskRestoreListener onTaskRestoreListener;

    public interface OnTaskRestoreListener {
        void onTaskRestore(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onTaskRestoreListener = (OnTaskRestoreListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTaskRestoreListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new DoneTaskAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void addTaskFromDB() {
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_DONE)}, DBHelper.TASK_DATE_COLUMN));
        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i), false);
        }

    }


    @Override
    public void moveTask(ModelTask task) {
        onTaskRestoreListener.onTaskRestore(task);
    }
}
