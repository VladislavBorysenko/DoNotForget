package com.example.donotforget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import com.example.donotforget.R;
import com.example.donotforget.Utils;
import com.example.donotforget.model.ModelTask;

import java.util.Calendar;

public class AddingTaskDialogFragment extends DialogFragment {
    //Для получения доступа к диалогу, например, во время нажатия, нужно определить ему слушатель.
    //Для этого используем интерфейс AddingTaskListener и паттерн "Наблюдатель".

    private AddingTaskListener addingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded(ModelTask newTask);

        void onTaskAddingCancel();
    }


    @Override
    public void onAttach(Activity activity) {
        //Проверяем, что активити реализует интерфейс AddingTaskListener, иначе будет выброшено исключение.
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingTaskListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Паттерн Билдер.
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //заголовок диалогового окна.
        builder.setTitle(R.string.dialog_title);

        //для работы с макетом диалога.
        LayoutInflater inflater = getActivity().getLayoutInflater();


        //находим макет диалога и все элементы в нём.
        View container = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        final EditText etTitle = tilTitle.getEditText();

        final TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate = tilDate.getEditText();

        final TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        final EditText etTime = tilTime.getEditText();

        //подсказки в элементах EditText.
        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        //Чтобы диалог отображался при вызове.
        builder.setView(container);

        final ModelTask task = new ModelTask();
        final Calendar calendar = Calendar.getInstance();//объект возвращает текущее время.

        //добавим 1 час, чтобы календарь срабатывал через 1 час,
        //если при создании задачи указана только дата без времени.
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Чтобы избежать накладывания "плавающих ярлыков" в поля ввода.
                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }
                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //аргумент имеет свойство Nullable, поэтому null-не ошибка
                        etDate.setText(null);
                    }
                };
                //реализуем отображение диалога
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });


        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Чтобы избежать накладывания "плавающих ярлыков" в поля ввода.
                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }
                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        etTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etTime.setText(null);
                    }
                };
                //реализуем отображение диалога
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });


        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //присваиваем заголовку таска значение из поля ввода.
                task.setTitle(etTitle.getText().toString());
                if(etDate.length() !=0 || etTime.length() !=0){
                    task.setDate(calendar.getTimeInMillis());
                }

                //Таск создаётся в методе onCreateDialog.
                //Мы передаём созданный таск в метод addingTaskListener.onTaskAdded(task)
                addingTaskListener.onTaskAdded(task);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                addingTaskListener.onTaskAddingCancel();
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                //блокируем создание пустых тасков.
                if (etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }


                //добавляем слушатель события "изменение текста".
                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else {
                            //делаем активной кнопку positiveButton
                            positiveButton.setEnabled(true);
                            //отключаем отображение ошибки
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        //результатом метода onCreateDialog является созданный с помощью билдера alertDialog.
        return alertDialog;
    }
}
