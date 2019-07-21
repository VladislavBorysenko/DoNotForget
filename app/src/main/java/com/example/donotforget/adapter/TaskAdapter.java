package com.example.donotforget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.donotforget.fragment.TaskFragment;
import com.example.donotforget.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //базовый абстрактный класс для CurrentTaskAdapter и DoneTaskAdapter

    List<Item> items;
    TaskFragment taskFragment;

    public TaskAdapter(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    //получить элемент списка по позиции
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

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
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

        public TaskViewHolder(@NonNull View itemView, TextView title, TextView date,CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }
}
