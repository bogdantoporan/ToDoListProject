package com.disertatie.todolist;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dan_Petran on 9/11/13.
 */
public class CheckForTasks implements Runnable
{

    ArrayList<ToDoModel> tasks;
    CheckForTasks thisItem;
    MainActivity instance;

    public CheckForTasks(ArrayList<ToDoModel> _tasksLst, MainActivity _instance)
    {
        this.tasks = _tasksLst;
        this.instance = _instance;
    }
    @Override
    public void run()
    {
        int i = 0;
        int dot = 200;      // Length of a Morse Code "dot" in milliseconds
        int dash = 500;     // Length of a Morse Code "dash" in milliseconds
        int short_gap = 200;    // Length of Gap Between dots/dashes
        int medium_gap = 500;   // Length of Gap Between Letters
        int long_gap = 1000;    // Length of Gap Between Words
        long[] pattern = {
                0,  // Start immediately
                dot, short_gap, dot, short_gap, dot,    // s
                medium_gap,
                dash, short_gap, dash, short_gap, dash, // o
                medium_gap,
                dot, short_gap, dot, short_gap, dot,    // s
                long_gap
        };

        while(true)
        {
        for(ToDoModel model:tasks)
        {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(instance).setSmallIcon(R.drawable.task).setContentTitle("ToDoList").setContentText(model.GetToDoName()).setVibrate(pattern);

            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(model.GetToDoBy());
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
                Date dateCurrent = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(timeStamp);

                long diff = date.getTime() - dateCurrent.getTime();

                long diffMinutes = diff / (60 * 1000);
                if (diffMinutes <= 30 && !model.GetTaskNotified() && diffMinutes > 0)
                {
                    NotificationManager mNotificationManager = (NotificationManager) instance.getSystemService(Context.NOTIFICATION_SERVICE); // mId allows you to update the notification later on.
                    mNotificationManager.notify(i, mBuilder.build());
                    i++;
                    model.SetTaskNotified();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        }
    }
}
