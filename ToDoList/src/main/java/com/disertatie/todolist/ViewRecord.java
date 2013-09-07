package com.disertatie.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

/**
 * Created by Dan_Petran on 9/6/13.
 */
public class ViewRecord extends Activity implements View.OnClickListener
{
    private Button btn_addrecord;
    private Button btn_deleterecord;
    private EditText txtname, txtdescription, txtby;
    DatabaseAccess db;
    ToDoModel td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecord);

        txtname = (EditText) findViewById(R.id.txtname);
        txtdescription = (EditText) findViewById(R.id.txtdescription);
        txtby = (EditText) findViewById(R.id.txtby);

        btn_addrecord = (Button) findViewById(R.id.btn_addrecord);
        btn_addrecord.setOnClickListener(this);

        btn_deleterecord = (Button) findViewById(R.id.btn_deleterecord);
        btn_deleterecord.setOnClickListener(this);

        db = new DatabaseAccess(getApplicationContext());
        db.getWritableDatabase();

        ToDoModel item = new ToDoModel();
        String extraValue = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            extraValue = extras.getString("EXTRA_SESSION_ID");
        }

        try {
             item = db.GetToDoListItem(extraValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtname.setText(item.GetToDoName());
        txtdescription.setText(item.GetToDoDescription());
        txtby.setText(item.GetToDoBy());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_addrecord:
                    db = new DatabaseAccess(getApplicationContext());
                    db.getWritableDatabase();
                    td = new ToDoModel(0, txtname.getText().toString(), txtdescription.getText().toString(), txtby.getText().toString());
                    db.UpdateToDo(td);

                    Toast.makeText(ViewRecord.this, "Task successfully updated!",
                            Toast.LENGTH_LONG).show();

                Intent updateMain = new Intent(ViewRecord.this,
                        MainActivity.class);
                startActivity(updateMain);
                break;
            case R.id.btn_deleterecord:

                db = new DatabaseAccess(getApplicationContext());
                db.getWritableDatabase();
                String toDoName = txtname.getText().toString();
                db.DeleteToDoItem(toDoName);
                db.close();

                Intent mainActivity = new Intent(ViewRecord.this,
                        MainActivity.class);
                startActivity(mainActivity);
                break;
            default:
                break;
        }
    }
}