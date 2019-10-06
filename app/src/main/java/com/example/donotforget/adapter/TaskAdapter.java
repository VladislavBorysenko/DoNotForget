package com.example.donotforget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.donotforget.fragment.TaskFragment;
import com.example.donotforget.model.Item;
import com.example.donotforget.model.ModelSeparator;
import com.example.donotforget.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //базовый абстрактный класс для CurrentTaskAdapter и DoneTaskAdapter

    List<Item> items;
    TaskFragment taskFragment;

    //Переменные показывают, какие сепараторы есть в данный момент.
    public boolean containsSeparatorOverdue;
    public boolean containsSeparatorToday;
    public boolean containsSeparatorTomorrow;
    public boolean containsSeparatorFuture;

    public TaskAdapter(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    //получить элемент списка по позиции
    public Item getItem(int position) {
        return items.get(position);
    }

    public void updateTask(ModelTask newTask) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).isTask()) {
                ModelTask task = (ModelTask) getItem(i);
                if (newTask.getTimeStamp() == task.getTimeStamp()) {
                    removeItem(i);
                    getTaskFragment().addTask(newTask, false);
                }
            }
        }
    }

    //добавить элемент списка
    public void addItem(Item item) {
        items.add(item);
        //оповещение о добавлении нового элемента списка
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        //оповещение о добавлении нового элемента списка
        notifyItemInserted(location);
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
            //Удаляем сепараторы
            if (location - 1 >= 0 && location <= getItemCount() - 1) {
                if (!getItem(location).isTask() && !getItem(location - 1).isTask()) {

                    ModelSeparator separator = (ModelSeparator) getItem(location - 1);

                    switch (separator.getType()) {
                        case ModelSeparator.TYPE_OVERDUE:
                            containsSeparatorOverdue = false;
                            break;
                        case ModelSeparator.TYPE_TODAY:
                            containsSeparatorToday = false;
                            break;
                        case ModelSeparator.TYPE_TOMORROW:
                            containsSeparatorTomorrow = false;
                            break;
                        case ModelSeparator.TYPE_FUTURE:
                            containsSeparatorFuture = false;
                            break;
                    }
                    items.remove(location - 1);
                    notifyItemRemoved(location - 1);
                }

            } else if (getItemCount() - 1 >= 0 && !getItem(getItemCount() - 1).isTask()) {
                ModelSeparator separator = (ModelSeparator) getItem(getItemCount() - 1);
                switch (separator.getType()) {
                    case ModelSeparator.TYPE_OVERDUE:
                        containsSeparatorOverdue = false;
                        break;
                    case ModelSeparator.TYPE_TODAY:
                        containsSeparatorToday = false;
                        break;
                    case ModelSeparator.TYPE_TOMORROW:
                        containsSeparatorTomorrow = false;
                        break;
                    case ModelSeparator.TYPE_FUTURE:
                        containsSeparatorFuture = false;
                        break;
                }
                int loc = getItemCount() - 1;
                items.remove(loc);
                notifyItemRemoved(loc);
            }
        }
    }

    public void removeAllItems() {
        if (getItemCount() != 0) {
            //Чтобы не удалять в цикле, присвоим пустой список.
            items = new ArrayList<>();
            notifyDataSetChanged();
            containsSeparatorOverdue = false;
            containsSeparatorToday = false;
            containsSeparatorTomorrow = false;
            containsSeparatorFuture = false;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //protected - чтобы иметь доступ из классов-наследников
    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(@NonNull View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    protected class SeparatorViewHolder extends RecyclerView.ViewHolder {
        protected TextView type;

        public SeparatorViewHolder(View itemView, TextView type) {
            super(itemView);
            this.type = type;
        }
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}
