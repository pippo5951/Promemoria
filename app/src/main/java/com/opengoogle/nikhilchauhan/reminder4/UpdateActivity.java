package com.opengoogle.nikhilchauhan.reminder4;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    int myear, mdate, mmonth, mmin, mhour;
    int num = 1;
    String update_id;
    int id_number;

    DataBaseHelper myDb;
    AlarmDatabase myAlarmDb;

    EditText date_text, time_text, name_text, desc_text;
    TextView id_text;
    Button button_update_rem;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent my_intent;
    Context context;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity_2);
        myDb = new DataBaseHelper(this);
        myAlarmDb = new AlarmDatabase(this);

        date_text = (EditText) findViewById(R.id.date_text);
        time_text = (EditText) findViewById(R.id.time_text);
        id_text = (TextView) findViewById(R.id.id_text);
        name_text = (EditText) findViewById(R.id.name_text);
        desc_text = (EditText) findViewById(R.id.desc_text);

        myDb = new DataBaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            update_id = extras.getString("id");
            //The key argument here must match that used in the other activity
        }
        id_number = Integer.parseInt(update_id);


        int number = 1;







        button_update_rem = (Button) findViewById(R.id.button_update_rem);
        id_text.setText(update_id);


        this.context = this;


        // initialize the alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        /*
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            //show message
            Toast.makeText(UpdateActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();
            return;
        }
        int result = Integer.parseInt(get_string);

        while (res.moveToPosition(result)) {
            id_text.setText("Id :" + res.getString(0));
            name_text.setText("Name :" + res.getString(1) + "\n");
            date_text.setText("Date :" + res.getString(2) + "\n");
            time_text.setText("Time :" + res.getString(3) + "\n");
            desc_text.setText("Description: "+res.getString(4)+"\n\n");
        }
        //show the data
        */




        button_update_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.UpdateData(id_text.getText().toString(), name_text.getText().toString(), date_text.getText().toString(), time_text.getText().toString(), desc_text.getText().toString());

                if (isUpdate == true) {
                    Toast.makeText(UpdateActivity.this, "Updation Successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UpdateActivity.this, "Updation UnSuccessful", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }




}
