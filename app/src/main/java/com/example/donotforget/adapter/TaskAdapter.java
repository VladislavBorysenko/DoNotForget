package com.example.donotforget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.donotforget.fragment.TaskFragment;
import com.example.donotforget.model.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> items;

    TaskFragment taskFragment;

    public TaskAdapter(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position) {
        return items.get(position);
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

    //пропишем условие, для безопасного удоления из списка
    public void removItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
        }
    }


    //переопределяем метод который будет возвращать размер массива элементов списка
    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;

        public TaskViewHolder(@NonNull View itemView, TextView title, TextView date) {
            super(itemView);
            this.title = title;
            this.date = date;
        }
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}
