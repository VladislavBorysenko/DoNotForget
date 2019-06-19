package com.example.donotforget;


import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.donotforget.adapter.TabAdapter;
import com.example.donotforget.fragment.SplashFragment;

public class MainActivity extends AppCompatActivity {
//создаем переменную fragmentManager менеджер для работы с фрагментами
    FragmentManager fragmentManager;
//создаем переменную PreferenceHelper которая содержит настройки проэкта
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализируем преференс хелпер
        PreferenceHelper.getInstance().init(getApplicationContext());
        //обьявляем обьект и получаем экземпляр с помощью метода getInstance
        //используем патерн синглТон
        preferenceHelper = PreferenceHelper.getInstance();

        fragmentManager = getFragmentManager();

        runSplash();

        setUI();
    }

    public void runSplash() {
        //код запуска сплэшскрина.
        //сплешскрин будет показываться, при соответствующем значении чекбокса.
        if (!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            //транзакция, которая отображает сплэшскрин на экране.
            //метод replace заменяет один фрагмент на другой.
            //метод addToBackStack добавляет транзакцию в стек Task-а.
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)//последовательность экранов
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //добавление меню в action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);

        //берем значение из файла preferences при запуске приложения.
        splashItem.setChecked(preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_splash) {
            //при каждом нажатии меняем значение переключателя на противоположное.
            item.setChecked(!item.isChecked());

            //сохраняем значение в файл preferences.
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //подключаем ToolBar программно.
    //метод setUI отвечает за пользовательский интерфейс.
    private void setUI() {

        //подключили Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = findViewById(R.id.pager);
        TabAdapter tabAdapter = new TabAdapter(fragmentManager,2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
