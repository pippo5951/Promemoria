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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity {
    int myear, mdate, mmonth, mmin, mhour;
    int num = 1;
    String add_rem_id="1";

    DataBaseHelper myDb;
    AlarmDatabase myAlarmDb;

    EditText name_text, desc_text;
    Button date_text,time_text;
    TextView id_text;
    Button button_add_rem;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent my_intent,my_intent_2;
    Context context;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder_activity);
        int number = 1;

        myDb = new DataBaseHelper(this);


        date_text = (Button) findViewById(R.id.date_text);
        time_text = (Button) findViewById(R.id.time_text);
        id_text = (TextView) findViewById(R.id.id_text);
        name_text = (EditText) findViewById(R.id.name_text);
        desc_text = (EditText) findViewById(R.id.desc_text);

        this.context=this;


        button_add_rem = (Button) findViewById(R.id.button_add_rem);

        Cursor res = myDb.getAllData();


        if(res.getCount()==0)
        {
            id_text.setText("1");
        }
        else
        {
            res.moveToPosition(res.getCount() - 1);
            id_text.setText(res.getString(0));
            add_rem_id=id_text.getText().toString();


        }





        // initialize the alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        showDatePicker();
        show_time_picker();

        AddRem();

    }

    public void showDatePicker() {
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReminder.this,
                        android.R.style.Theme_DeviceDefault_Dialog, mDateListener, year, month, day);
                datePickerDialog.show();

            }
        });
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                mmonth = month;
                myear = year;
                mdate = dayOfMonth;

                String date = dayOfMonth + "/" + month + "/" + year;
                date_text.setText(date);
                Log.d("date", year + "/" + month + "/" + dayOfMonth);

            }
        };


    }

    public void show_time_picker() {
        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                int sec = cal.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReminder.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        mTimeListener, hour, min, false);
                timePickerDialog.show();
            }
        });
        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mhour = hourOfDay;
                mmin = minute;
                Log.d("hour", hourOfDay + ":" + minute);
                if (hourOfDay > 12) {
                    hourOfDay -= 12;
                }
                String time = hourOfDay + ":" + minute;
                time_text.setText(time);
            }
        };
    }

    public void AddRem() {
        button_add_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(name_text.getText().toString(), date_text.getText().toString(), time_text.getText().toString(), desc_text.getText().toString());

                if (isInserted == true) {
                    Toast.makeText(AddReminder.this, "allarme programmato correttamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddReminder.this, "Errore durante l'impostazione dell'allarme", Toast.LENGTH_LONG).show();
                }
                final Calendar calendar = Calendar.getInstance();
                my_intent = new Intent(AddReminder.this, AlarmReceiver.class);


                calendar.set(Calendar.DATE, mdate);
                calendar.set(Calendar.DATE, mmonth);
                calendar.set(Calendar.DATE, myear);
                calendar.set(Calendar.DATE, mdate);
                calendar.set(Calendar.HOUR_OF_DAY, mhour);
                calendar.set(Calendar.MINUTE, mmin);

                Log.d("alarm set date:", mdate + "/" + mmonth + "/" + myear);
                Log.e("alarm set time", mhour + ":" + mmin);


                my_intent.putExtra("extra", "alarm on");
                my_intent.putExtra("name",name_text.getText().toString());
                my_intent.putExtra("desc",desc_text.getText().toString());
                num += 1;


                pendingIntent = PendingIntent.getBroadcast(AddReminder.this, num, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Intent it = new Intent( AddReminder.this, MainActivity.class );
                startActivity(it);
            }

        });
    }
}
