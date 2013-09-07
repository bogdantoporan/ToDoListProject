package com.disertatie.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

         private Context context;

         private final String[] countries;

         public CustomAdapter(Context context, String[] countries) {
                 this.context = context;
                 this.countries = countries;
         }

         public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View gridView;

         if (convertView == null) {
                         gridView = new View(context);

                         gridView = inflater.inflate(R.layout.tasks, null);
                         TextView textView = (TextView) gridView.findViewById(R.id.label);
                         textView.setText(countries[position]);
                         ImageView flag = (ImageView) gridView .findViewById(R.id.flag);
                         flag.setImageResource(R.drawable.task);
                     }
                     else
                     {
                        gridView = (View) convertView;
                     }
                return gridView;
         }

         @Override
         public int getCount() {
                return countries.length;
         }

         @Override
         public Object getItem(int position) {
                return null;
         }

         @Override
         public long getItemId(int position) {
                return 0;
         }
 }
