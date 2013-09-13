package com.disertatie.todolist;

import android.app.Activity;

/**
 * Created by Dan on 9/6/13.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecord extends Activity implements OnClickListener {

    private Button btn_addrecord;
    private EditText txtname, txtdescription, txtby;
    DatabaseAccess db;
    ToDoModel td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecord);

        txtname = (EditText) findViewById(R.id.txtname);
        txtdescription = (EditText) findViewById(R.id.txtdescription);
        txtby = (EditText) findViewById(R.id.txtby);

        btn_addrecord = (Button) findViewById(R.id.btn_addrecord);

        btn_addrecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_addrecord:

                if (txtname.getText().toString().equals("")
                        || txtdescription.getText().toString().equals("") || txtby.getText().toString().equals("") ) {
                    Toast.makeText(AddRecord.this, "Please fill in all the fields",
                            Toast.LENGTH_LONG).show();
                } else {

                    db = new DatabaseAccess(getApplicationContext());
                    db.getWritableDatabase();

                    td = new ToDoModel(0, txtname.getText().toString(), txtdescription.getText().toString(), txtby.getText().toString());
                    db.addProduct(td);
                    Toast.makeText(AddRecord.this, "New task successfully added!",
                            Toast.LENGTH_LONG).show();
                }

                Intent addintent = new Intent(AddRecord.this,
                        MainActivity.class);
                startActivity(addintent);
                break;

            default:
                break;
        }
    }
}