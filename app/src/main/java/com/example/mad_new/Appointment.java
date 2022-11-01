package com.example.mad_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        String id = databaseReference.push().getKey();
        final EditText name = findViewById(R.id.name);
        final EditText date = findViewById(R.id.date);
        final EditText time = findViewById(R.id.time);
        final EditText contact = findViewById(R.id.contact);
        final Button submit = findViewById(R.id.submit_btn);
        final ImageButton back = findViewById(R.id.back);
        final DatePickerDialog[] picker = new DatePickerDialog[1];


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar =Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);

                //date picker dialog
                picker[0] = new DatePickerDialog(Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i2 +"/"+(i1+1)+"/"+ i);
                    }
                },year,month,day);
                picker[0].show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nameText = name.getText().toString();
                final String dateText= date.getText().toString();
                final String timeText = time.getText().toString();
                final String contactText = contact.getText().toString();
                String apid =id;


                if(contactText.isEmpty()) {
                    contact.setError("Contact is empty");
                    contact.requestFocus();
                    return;
                }else if(nameText.isEmpty()){
                    name.setError("Name is empty");
                    name.requestFocus();
                    return;
                }else if(dateText.isEmpty()) {
                    date.setError("Image is empty");
                    date.requestFocus();
                    return;
                }else if(timeText.isEmpty()){
                    time.setError("Time is empty");
                    time.requestFocus();
                }else{
                    databaseReference.child("appointment").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("appointment").child(apid).child("id").setValue(id);
                            databaseReference.child("appointment").child(apid).child("name").setValue(nameText);
                            databaseReference.child("appointment").child(apid).child("date").setValue(dateText);
                            databaseReference.child("appointment").child(apid).child("time").setValue(timeText);
                            databaseReference.child("appointment").child(apid).child("contact").setValue(contactText);
                            Toast.makeText(Appointment.this, "Appointment add successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Appointment.this,home.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }





            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Appointment.this,home.class));
            }
        });
    }
}