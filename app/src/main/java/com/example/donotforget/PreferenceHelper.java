package com.example.donotforget;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    //данная константа является ключём
    //для свойства (отображать сплешскрин или нет) в файле preferences.
    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";


    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;



    //////////Паттерн синглтон.
    private PreferenceHelper() {

    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }
//////////

    public void init(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }


    //передаём состояние чекбокса (отображать сплешскрин или нет) в файл preferences
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //получаем состояние чекбокса (отображать сплешскрин или нет) из файла preferences
    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

}
