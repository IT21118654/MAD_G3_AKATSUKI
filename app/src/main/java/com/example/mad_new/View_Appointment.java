package com.example.mad_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class View_Appointment extends AppCompatActivity {


    appointmentAdapter adapter;
    RecyclerView  recyclerView;
    ArrayList<appointmentModel> stylesList = new ArrayList<>();
    ArrayList<appointmentModel> usersList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ImageButton btn_back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllHairstyles();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(View_Appointment.this, home.class));
            }
        });
    }

    private void getAllHairstyles()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("appointment");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                collectStyles((Map<String,Object>) snapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void collectStyles(Map<String,Object> users) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            String name=singleUser.get("name").toString();
            String contact=singleUser.get("contact").toString();
            String date=singleUser.get("date").toString();
            String time=singleUser.get("time").toString();
            String id=singleUser.get("id").toString();
            appointmentModel tempstyle=new appointmentModel(id,time,date,name,contact);
            stylesList.add((tempstyle) );
        }

        displayStyles();

    }
    private void displayStyles()
    {


        usersList2=new ArrayList<>();
        for(int i=0;i<stylesList.size();i++)
        {
            System.out.println("Modalskldskhdfksbkha"+stylesList.get(i).getName());
            usersList2.add(new appointmentModel(stylesList.get(i).getId(), stylesList.get(i).getTime(), stylesList.get(i).getDate(),stylesList.get(i).getName(),stylesList.get(i).getPhone()));
            appointmentAdapter adapter = new appointmentAdapter(usersList2, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }


    }

}