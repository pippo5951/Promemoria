package com.opengoogle.nikhilchauhan.reminder4;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstUpdate extends AppCompatActivity {

    EditText update_id_text;
    Button up_rem;
    String id;
    Intent my_intent;
    Context context;
    DataBaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_update_activity);

        this.context=this;

        update_id_text=(EditText)findViewById(R.id.update_id_text);
        up_rem=(Button)findViewById(R.id.up_rem);
        updateRem();

        myDb = new DataBaseHelper(this);





    }
    void updateRem()
    {
        up_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();

                if (res.getCount() == 0) {
                    //show message
                    Toast.makeText(FirstUpdate.this,"Promemoria non trovato",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    id=update_id_text.getText().toString();

                    my_intent = new Intent(FirstUpdate.this,UpdateActivity.class);
                    my_intent.putExtra("id",id);
                    startActivity(my_intent);
                }







            }
        });
    }
}
