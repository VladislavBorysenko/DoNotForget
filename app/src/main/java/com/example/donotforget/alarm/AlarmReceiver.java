package com.example.donotforget.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.donotforget.MainActivity;
import com.example.donotforget.MyApplication;
import com.example.donotforget.R;

//Данный класс позволяет принимать сообщения из других андроид предложений
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //при вызове ресивера мы будем передовать ему параметры
        //имя задачи
        String title = intent.getStringExtra("title");
        //время создания задачи
        long timeStamp = intent.getLongExtra("time_Stamp", 0);
        //цвет который будет определять приоритет задачи
        int color = (int) intent.getLongExtra("color", 0);
        //Запускает наше главное активити при нажатии на нотификацию
        Intent resultIntent = new Intent(context, MainActivity.class);

        if (MyApplication.isActivityVisible()) {
            resultIntent = intent;
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) timeStamp,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("DoNotForget");
        builder.setContentText(title);
        builder.setColor(context.getResources().getColor(color));
        builder.setSmallIcon(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);

        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |=Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) timeStamp, notification);



    }
}
