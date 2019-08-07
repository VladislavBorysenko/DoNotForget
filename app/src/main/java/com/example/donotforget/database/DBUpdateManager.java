package com.example.donotforget.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.donotforget.model.ModelTask;

public class DBUpdateManager {

    SQLiteDatabase database;

    //создаем конструктор класса DBUpdateManager
    DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    //методы для обнавления каждого столбца
    public void title(long timeStamp, String title) {
        update(DBHelper.TASK_TITLE_COLUMN, timeStamp, title);
    }

    public void date(long timeStamp, long date) {
        update(DBHelper.TASK_DATE_COLUMN, timeStamp, date);
    }

    public void priority(long timeStamp, int priority) {
        update(DBHelper.TASK_PRIORITY_COLUMN, timeStamp, priority);
    }

    public void status(long timeStamp, int status) {
        update(DBHelper.TASK_STATUS_COLUMN, timeStamp, status);
    }

    //метод обнавления всего таска
    public void task(ModelTask task) {
        title(task.getTimeStamp(), task.getTitle());
        date(task.getTimeStamp(), task.getDate());
        priority(task.getTimeStamp(), task.getPriority());
        status(task.getTimeStamp(), task.getStatus());
    }

    private void update(String column, long key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " - " + key, null);
    }

    //прергружаем метод update
    private void update(String column, long key, long value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " - " + key, null);
    }
}
