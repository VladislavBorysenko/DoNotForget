package com.example.donotforget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.donotforget.R;

import java.util.Calendar;

public class AddingTaskDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //паттерн билдер

        final AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        //методом setTitle установим загооловок окна из файла string/dialog_title

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_task, null);

        TextInputLayout tilTitle = container.findViewById(R.id.tilDialogTaskTitle);
        EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate=tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        EditText etTime=tilTime.getEditText();


        //присваиваем заголовку текстовые ресурсы методом setHint через метод getResources
        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment datePickerFragment = new DatePickerFragment(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        Calendar dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        etDate.setText();
                    }
                }
            };
        });

        return super.onCreateDialog(savedInstanceState);
    }
}
