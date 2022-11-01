package com.example.mad_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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


public class Feedback extends AppCompatActivity {

    RecyclerView recyclerView;
    feedbackAdapter adapter;
    ArrayList<feedbackModel> arrayList = new ArrayList<>();
    ArrayList<feedbackModel> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Button add= findViewById(R.id.feedback_btn);
        final ImageButton back =findViewById(R.id.back);
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllFeedback();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Feedback.this,My_feedback.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Feedback.this,home.class));
            }
        });
    }
    public void getAllFeedback()
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("feedback");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    collectFeedback((Map<String,Object>) snapshot.getValue());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    private void collectFeedback(Map<String,Object> users) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            String name=singleUser.get("name").toString();
            String feedback=singleUser.get("feedback").toString();
            String id=singleUser.get("id").toString();
            feedbackModel tempstyle=new feedbackModel(name,feedback,id);
            arrayList.add((tempstyle) );
        }

        displayStyles();

    }
    private void displayStyles()
    {


        arrayList2=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++)
        {
            System.out.println("Modalskldskhdfksbkha"+arrayList.get(i).getName());
            arrayList2.add(new feedbackModel(arrayList.get(i).getName(),   arrayList.get(i).getFeedback(), arrayList.get(i).getId()));
            feedbackAdapter adapter = new feedbackAdapter(arrayList2, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }


    }

}