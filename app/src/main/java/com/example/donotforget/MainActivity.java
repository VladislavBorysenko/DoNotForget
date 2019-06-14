package com.example.donotforget;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализируем преференс хелпер
        PreferenceHelper.getInstance().init(getApplicationContext());
        //обьявляем обьект и получаем экземпляр с помощью метода getInstance
        preferenceHelper=PreferenceHelper.getInstance();

        fragmentManager = getFragmentManager();
        runSplash();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
    splashItem.setChecked(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        int id = item.getItemId();

        if (id==R.id.action_splash){
            //Следим за состоянием флага и при нажатии меняем его
            item.setChecked(!item.isChecked());
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void runSplash(){

        /*метод рансплеш проверяет параметр флага в преференс хелпер и взависимости от
        состояния показывает или не показывает сплеш скрин */

        //Реализуем зависимость показа сплеш скрина от состояния флага

        if (!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            //транзакция которая отобразит наш сплешскрин на экране
            fragmentManager.beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
