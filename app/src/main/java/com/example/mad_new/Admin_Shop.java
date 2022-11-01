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

public class Admin_Shop extends AppCompatActivity {

    shopAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<productModel> stylesList = new ArrayList<>();
    ArrayList<productModel> usersList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shop);

        final Button add = findViewById(R.id.shop_add);
        final ImageButton back = findViewById(R.id.back);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Shop.this, Add_products.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Shop.this, Admin_home.class));
            }
        });

        getAllProducts();
    }

    private void getAllProducts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                collectStyles((Map<String, Object>) snapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void collectStyles(Map<String, Object> users) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            String name = singleUser.get("name").toString();
            String price = singleUser.get("price").toString();
            String id = singleUser.get("id").toString();
            String image = singleUser.get("image").toString();
            productModel tempstyle = new productModel(name, price, image, id);
            stylesList.add((tempstyle));
        }

        displayStyles();

    }

    private void displayStyles() {
        usersList2 = new ArrayList<>();
        for (int i = 0; i < stylesList.size(); i++) {
            System.out.println("Modalskldskhdfksbkha" + stylesList.get(i).getName());
            usersList2.add(new productModel(stylesList.get(i).getName(), stylesList.get(i).getPrice(), stylesList.get(i).getImage(), stylesList.get(i).getId()));
            shopAdapter adapter = new shopAdapter(usersList2, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }


    }
}