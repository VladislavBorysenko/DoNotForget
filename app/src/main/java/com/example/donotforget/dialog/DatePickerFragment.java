package com.example.donotforget.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    //создаем метод Dialog и внем создаем обьект календарь
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        //инициализируем переменные: годб месяц, день
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
        //метод возвращает DatePickerDialog,  в параметрах: у него актвити которое мы получаем
        //методом getActivity(), ключевое слово this(в данном случае это ссылка на OnDateSetListener
        //и переменные календаря year,month,day
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
