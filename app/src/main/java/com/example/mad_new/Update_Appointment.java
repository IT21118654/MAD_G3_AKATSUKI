package com.example.mad_new;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Update_Appointment extends AppCompatActivity {

    String name, id, contact, date,time;
    EditText txt_name, txt_id, txt_contact, txt_date,txt_time;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        contact=getIntent().getStringExtra("contact");
        date=getIntent().getStringExtra("time");
        time=getIntent().getStringExtra("date");

        btn_update=findViewById(R.id.update);
        txt_name = findViewById(R.id.name);
        txt_id = findViewById(R.id.id);
        txt_contact=findViewById(R.id.contact);
        txt_date=findViewById(R.id.date);
        txt_time=findViewById(R.id.time);

        txt_id.setText(id);
        txt_name.setText(name);
        txt_contact.setText(contact);
        txt_date.setText(date);
        txt_time.setText(time);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        final EditText id = findViewById(R.id.id);
        final EditText name = findViewById(R.id.name);
        final EditText date = findViewById(R.id.date);
        final EditText time = findViewById(R.id.time);
        final EditText contact = findViewById(R.id.contact);
        final Button update = findViewById(R.id.update);
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
                picker[0] = new DatePickerDialog(Update_Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i2 +"/"+(i1+1)+"/"+ i);
                    }
                },year,month,day);
                picker[0].show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idText = id.getText().toString();
                final String nameText = name.getText().toString();
                final String dateText= date.getText().toString();
                final String timeText = time.getText().toString();
                final String contactText = contact.getText().toString();



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
                            databaseReference.child("appointment").child(idText).child("id").setValue(idText);
                            databaseReference.child("appointment").child(idText).child("name").setValue(nameText);
                            databaseReference.child("appointment").child(idText).child("date").setValue(dateText);
                            databaseReference.child("appointment").child(idText).child("time").setValue(timeText);
                            databaseReference.child("appointment").child(idText).child("contact").setValue(contactText);
                            Toast.makeText(Update_Appointment.this, "Appointment updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Update_Appointment.this,View_Appointment.class));
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
                startActivity(new Intent(Update_Appointment.this,View_Appointment.class));
            }
        });
    }
}