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

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText contact = findViewById(R.id.contact);
        final EditText password = findViewById(R.id.password);
        final Button sign_in = findViewById(R.id.btn_sign_in);
        final ImageButton back_btn = findViewById(R.id.back);
        final Button register = findViewById(R.id.register);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String contactText = contact.getText().toString();
                final String passwardText = password.getText().toString();

                if(contactText.isEmpty()||passwardText.isEmpty()){
                    Toast.makeText(Login.this,"Please enter your mobile or password",Toast.LENGTH_SHORT).show();
                }else if(contactText.equals("12345678")&& passwardText.equals("admin123#")){
                    startActivity(new Intent(Login.this, Admin_home.class));
                }
                else{
                   databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.hasChild(contactText)){
                               final String getPassword = snapshot.child(contactText).child("password").getValue(String.class);

                               if(getPassword.equals(passwardText)){
                                   Toast.makeText(Login.this,"Successfuly Login",Toast.LENGTH_SHORT).show();
                                   //pass data
                                   String nameFromDB = snapshot.child(contactText).child("name").getValue(String.class);
                                   Intent intent1 = new Intent(getApplicationContext(), My_feedback.class);
                                   intent1.putExtra("Name",nameFromDB);

                                   //next activity
                                   startActivity(new Intent(Login.this, home.class));
                                   finish();


                               }else{
                                   Toast.makeText(Login.this,"Wrong password",Toast.LENGTH_SHORT).show();
                               }
                           }else{
                               Toast.makeText(Login.this,"Wrong mobile number",Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Second_Screen.class));
            }
        });




    }
}