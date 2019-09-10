package com.example.donotforget.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.donotforget.MainActivity;
import com.example.donotforget.R;
import com.example.donotforget.adapter.TaskAdapter;
import com.example.donotforget.model.Item;
import com.example.donotforget.model.ModelTask;

public abstract class TaskFragment extends Fragment {
    //базовый абстрактный класс для CurrentTaskFragment и DoneTaskFragment

    //protected - чтобы иметь доступ из классов-наследников
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected TaskAdapter adapter;

    public MainActivity activity;

    public AlarmHelper alarmHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        alarmHelper = AlarmHelper.getInstance();

        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB) {

        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);

                //добавляем таски отсортированными по дате.
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;//?цикл прерывается при нахождении первого элемента с бОльшей датой.
                }
            }
        }

        if (position != -1) {
            adapter.addItem(position, newTask);
        } else {
            adapter.addItem(newTask);
        }

        //для новых тасков (saveToDB==true) будем выполнять сохраниние в БД
        if (saveToDB) {
            activity.dbHelper.saveTask(newTask);
        }
    }

    //вызов диалогового окна для удаления таска
    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = adapter.getItem(location);

        if (item.isTask()) {

            ModelTask removingTask = (ModelTask) item;
            final long timeStamp = removingTask.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    adapter.removeItem(location);
                    isRemoved[0] = true;
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), R.string.removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        //по нажатию на отмену в снекбаре, таск восстанавливается в БД
                        @Override
                        public void onClick(View view) {
                            addTask(activity.dbHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View view) {
                            //метод срабатывает, когда снекбар появляется на экране

                        }

                        @Override
                        public void onViewDetachedFromWindow(View view) {
                            //метод срабатывает, когда снекбар исчезает с экрана
                            if (isRemoved[0]) {
                                alarmHelper.removeAlarm(timeStamp);
                                //если не была нажата кнопка отмены удаления, то таск удаляется окончательно з БД
                                activity.dbHelper.removeTask(timeStamp);
                            }
                        }
                    });
                    snackbar.show();

                    dialogInterface.dismiss();
                }
            });
            dialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                }
            });
        }
        dialogBuilder.show();
    }

    public abstract void findTasks(String title);

    public abstract void addTaskFromDB();

    public abstract void moveTask(ModelTask task);
}
