package com.example.mad_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Add_products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        final EditText sid = findViewById(R.id.id);
        final EditText name = findViewById(R.id.name);
        final EditText price = findViewById(R.id.price);
        final EditText image = findViewById(R.id.image);
        final Button submit = findViewById(R.id.submit_btn);
        final ImageButton back = findViewById(R.id.back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String idText = sid.getText().toString();
                final String nameText = name.getText().toString();
                final String priceText = price.getText().toString();
                final String imageText = image.getText().toString();

                if(idText.isEmpty()){
                    sid.setError("id is required");
                    sid.requestFocus();
                    return;
                }else if(nameText.isEmpty()){
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }else if(priceText.isEmpty()){
                    price.setError("Price is required");
                    price.requestFocus();
                    return;
                }else if(imageText.isEmpty()){
                    image.setError("image is required");
                    image.requestFocus();
                    return;
                }else{
                    databaseReference.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(idText)) {
                            Toast.makeText(Add_products.this, "Id number already entered", Toast.LENGTH_SHORT).show();
                        }else {
                            databaseReference.child("products").child(idText).child("id").setValue(idText);
                            databaseReference.child("products").child(idText).child("name").setValue(nameText);
                            databaseReference.child("products").child(idText).child("price").setValue(priceText);
                            databaseReference.child("products").child(idText).child("image").setValue(imageText);
                            Toast.makeText(Add_products.this, "Product add succesfully", Toast.LENGTH_SHORT).show();

                        }
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
                startActivity(new Intent(Add_products.this,Admin_Shop.class));
            }
        });


    }
}