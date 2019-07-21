package com.example.donotforget;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.donotforget.adapter.TabAdapter;
import com.example.donotforget.dialog.AddingTaskDialogFragment;
import com.example.donotforget.fragment.CurrentTaskFragment;
import com.example.donotforget.fragment.DoneTaskFragment;
import com.example.donotforget.fragment.SplashFragment;
import com.example.donotforget.fragment.TaskFragment;
import com.example.donotforget.model.ModelTask;


public class MainActivity extends AppCompatActivity
        implements AddingTaskDialogFragment.AddingTaskListener,
        CurrentTaskFragment.OnTaskDoneListener, DoneTaskFragment.OnTaskRestoreListener{

    FragmentManager fragmentManager;

    PreferenceHelper preferenceHelper;

    TabAdapter tabAdapter;

    TaskFragment currentTaskFragment;
    TaskFragment doneTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceHelper.getInstance().init(getApplicationContext());
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
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //добавление меню в action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
//        MenuItem secondItem = menu.findItem(R.id.second_Item);

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
//        else if (id == R.id.second_Item) {
//            Toast.makeText(getApplicationContext(), "Second Item", Toast.LENGTH_SHORT).show();
//        }

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


        //Создадим TabLayout и добавим вкладки
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = findViewById(R.id.pager);
        tabAdapter = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Toast.makeText(getApplicationContext(),"Selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        currentTaskFragment = (CurrentTaskFragment) tabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        doneTaskFragment = (DoneTaskFragment) tabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //создаём объект типа AddingTaskDialogFragment и вызываем его.
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "addingTaskDialogFragment");
            }
        });
    }

    @Override
    public void onTaskAdded(ModelTask newTask) {
        Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show();
        currentTaskFragment.addTask(newTask);

    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Task adding canceled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskRestore(ModelTask task) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
