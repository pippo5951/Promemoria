package com.opengoogle.nikhilchauhan.reminder4;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteReminder extends AppCompatActivity {
    int myear, mdate, mmonth, mmin, mhour;
    int num = 1;

    DataBaseHelper myDb;
    AlarmDatabase myAlarmDb;

    EditText delete_id_text;
    Button button_del_rem;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent my_intent;
    Context context;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_activity);
        int number = 1;

        myDb = new DataBaseHelper(this);
        myAlarmDb = new AlarmDatabase(this);


        delete_id_text = (EditText) findViewById(R.id.delete_id_text);
        button_del_rem=(Button)findViewById(R.id.button_del_rem);


        this.context = this;


        // initialize the alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        deleteRem();
    }

    public void deleteRem() {
        button_del_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.Deletedata(delete_id_text.getText().toString());
                if (deleteRows > 0) {
                    Toast.makeText(DeleteReminder.this, "Deletion Successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DeleteReminder.this, "No rows deletes", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
