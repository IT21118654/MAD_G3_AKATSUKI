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

public class Add_hairstyles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hairstyles);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        final EditText styleid = findViewById(R.id.sid);
        final EditText name = findViewById(R.id.sname);
        final EditText price = findViewById(R.id.price);
        final EditText image = findViewById(R.id.image);
        final Button submit = findViewById(R.id.submit_btn);
        final ImageButton back = findViewById(R.id.back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idText = styleid.getText().toString();
                final String nameText = name.getText().toString();
                final String priceText = price.getText().toString();
                final String imageText = image.getText().toString();

                if(idText.isEmpty()) {
                    styleid.setError("Id is empty");
                    styleid.requestFocus();
                    return;
                }else if(nameText.isEmpty()){
                    name.setError("Name is empty");
                    name.requestFocus();
                    return;
                }else if(priceText.isEmpty()){
                    price.setError("Price is empty");
                    price.requestFocus();
                    return;
                }else if(imageText.isEmpty()){
                    image.setError("Image is empty");
                    image.requestFocus();
                    return;
                }else{
                    databaseReference.child("styles").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(idText)){
                                Toast.makeText(Add_hairstyles.this, "Id number already entered", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("styles").child(idText).child("id").setValue(idText);
                                databaseReference.child("styles").child(idText).child("name").setValue(nameText);
                                databaseReference.child("styles").child(idText).child("price").setValue(priceText);
                                databaseReference.child("styles").child(idText).child("image").setValue(imageText);
                                Toast.makeText(Add_hairstyles.this, "Hairstyle add succesfully", Toast.LENGTH_SHORT).show();
                                finish();
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
                startActivity(new Intent(Add_hairstyles.this,Hair_Styles.class));
            }
        });

    }
}