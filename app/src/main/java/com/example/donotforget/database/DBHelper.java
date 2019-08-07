package com.example.donotforget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.donotforget.model.ModelTask;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DoNotForget_database";
    public static final String TASKS_TABLE = "tasks_table";

    public static final String TASK_TITLE_COLUMN = "tasks_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";

    //создаем приватную константу для создания таблици
    private static final String TASK_TABLE_CREATE_SCRIPT = "CREATE TABLE "
            + TASKS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + TASK_DATE_COLUMN + " LONG, " + TASK_PRIORITY_COLUMN + " INTEGER, "
            + TASK_STATUS_COLUMN + " INTEGER" + TASK_TIME_STAMP_COLUMN + " LONG);";


    public static final String SELECTION_STATUS = DBHelper.TASK_STATUS_COLUMN + " = ? ";

    private DBQueryManager queryManager;
    private DBUpdateManager updateManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        queryManager = new DBQueryManager(getReadableDatabase());
        updateManager = new DBUpdateManager(getWritableDatabase());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с помощью скрипта
        db.execSQL(TASK_TABLE_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //при помощи запроса DROP TABLE мы будем удолять эту таблицу
        db.execSQL("DROP TABLE " + TASKS_TABLE);
        //при помощи запроса onCreate будет ее пересоздавать с параметром db
        onCreate(db);
    }

    //создадим метод для сохранения тасков
    public void saveTask(ModelTask task) {
        //будем использвать обьект newValues, класса Values
        ContentValues newValues = new ContentValues();

        newValues.put(TASK_TITLE_COLUMN, task.getTitle());
        newValues.put(TASK_DATE_COLUMN, task.getDate());
        newValues.put(TASK_STATUS_COLUMN, task.getStatus());
        newValues.put(TASK_PRIORITY_COLUMN, task.getPriority());
        newValues.put(TASK_TIME_STAMP_COLUMN, task.getTimeStamp());

        getWritableDatabase().insert(TASKS_TABLE, null, newValues);
    }

    public DBQueryManager query() {
        return queryManager;
    }

    public DBUpdateManager update() {
        return updateManager;
    }

}


