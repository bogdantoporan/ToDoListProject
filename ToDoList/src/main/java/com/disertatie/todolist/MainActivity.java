package com.disertatie.todolist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends Activity
{

    GridView gridView;
    String[] TASKS;
    DatabaseAccess db;

         @Override
         public void onCreate(Bundle savedInstanceState)
         {

                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_main);



                 //TASKS[0] = "mumu";

                 db = new DatabaseAccess(getApplicationContext());
                 db.getWritableDatabase();

             ArrayList<ToDoModel> tasks = new ArrayList<ToDoModel>();

             try {
                 tasks = db.GetToDoList();
             } catch (ParseException e) {
                 e.printStackTrace();
             }

             int nbOfTasks = db.GetNbOfToDoItems();
             TASKS = new String[nbOfTasks];

             int i = 0;
             for(ToDoModel model:tasks)
             {
                 TASKS[i] = model.GetToDoName();
                 i++;
             }

             (new Thread(new CheckForTasks(tasks, this))).start();
             //db.close();


             gridView = (GridView) findViewById(R.id.gridView);
                 gridView.setAdapter(new CustomAdapter(this, TASKS));

                 gridView.setOnItemClickListener(new OnItemClickListener() {
                         public void onItemClick(AdapterView parent, View v, int position, long id) {
                                 //Toast.makeText(
                                 //                getApplicationContext(),
                                 //                ((TextView) v.findViewById(R.id.label)).getText(), Toast.LENGTH_SHORT).show();

                             String toSet = ((TextView) v.findViewById(R.id.label)).getText().toString();
                             Intent viewIntent = new Intent(MainActivity.this,
                                     ViewRecord.class);
                             viewIntent.putExtra("EXTRA_SESSION_ID", toSet);
                             startActivity(viewIntent);
                            }
                     });
             }

         @Override
         public boolean onCreateOptionsMenu(Menu menu)
         {
             MenuInflater inflater = getMenuInflater();
             inflater.inflate(R.menu.main, menu);
             return true;
         }

         @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
         @Override
         public boolean onOptionsItemSelected(MenuItem item)
         {
             switch (item.getItemId())
             {
                 case R.id.add:
                     Intent intent = new Intent(this,
                             AddRecord.class);
                     startActivity(intent);
                     return true;
                 default:
                     return super.onOptionsItemSelected(item);
             }
         }
}
