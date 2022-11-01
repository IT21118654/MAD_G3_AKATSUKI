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

public class Admin_hairstyle extends AppCompatActivity {



    hairstyleAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<hairstyleModel> stylesList = new ArrayList<>();
    ArrayList<hairstyleModel> usersList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_styles);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ImageButton btn_back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FirebaseRecyclerOptions<hairstyleModel> options =
//                new FirebaseRecyclerOptions.Builder<hairstyleModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("styles"),hairstyleModel.class)
//                        .build();
//        System.out.println("Ammo"+options);
//        adapter = new hairstyleAdapter(options);

//        recyclerView.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_hairstyle.this, Admin_home.class));
            }
        });

        getAllHairstyles();



    }

    private void getAllHairstyles()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("styles");
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
            String price=singleUser.get("price").toString();
            String id=singleUser.get("id").toString();
            String image=singleUser.get("image").toString();
            hairstyleModel tempstyle=new hairstyleModel(name,price,image,id);
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
            usersList2.add(new hairstyleModel(stylesList.get(i).getName(), stylesList.get(i).getPrice(),  stylesList.get(i).getImage(),stylesList.get(i).getId()));
            hairstyleAdapter adapter = new hairstyleAdapter(usersList2, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }


    }
}