package com.example.donotforget;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    //создаем константу значением которой есть наш ключ
    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

// Паттерн синглтон
    private PreferenceHelper() {

    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init (Context context){
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putBoolean (String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key){
        //ключ поле с настройкой
        return preferences.getBoolean(key, false);

    }
}
