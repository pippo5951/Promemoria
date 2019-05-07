package com.opengoogle.nikhilchauhan.reminder4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView add_reminder, update_reminder, delete_reminder, view_reminder;

    int myear, mdate, mmonth, mmin, mhour;
    int num = 1;

    DataBaseHelper myDb;


    Intent my_intent;
    Context context;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        add_reminder = (TextView) findViewById(R.id.add_reminder);
        update_reminder = (TextView) findViewById(R.id.update_reminder);
        delete_reminder = (TextView) findViewById(R.id.delete_reminder);
        view_reminder = (TextView) findViewById(R.id.view_reminder);

        myDb = new DataBaseHelper(this);


        add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AddReminder.class);
                startActivityForResult(myIntent, 0);
            }
        });
        update_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),FirstUpdate.class);
                startActivityForResult(myIntent, 1);
            }
        });
        delete_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), DeleteReminder.class);
                startActivityForResult(myIntent, 2);
            }
        });

        ViewRem();


    }


    public void ViewRem() {
        view_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    showMessage("Error ", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Name :" + res.getString(1) + "\n");
                    buffer.append("Date :" + res.getString(2) + "\n");
                    buffer.append("Time :" + res.getString(3) + "\n");
                    buffer.append("Description: "+res.getString(4)+"\n\n");
                }
                //show the data
                showMessage("Data", buffer.toString());
            }
        });
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}



