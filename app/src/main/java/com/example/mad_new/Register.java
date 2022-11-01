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

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
     private EditText fname ;
     private EditText lname ;
     private EditText phone ;
     private EditText password ;
     private EditText spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final ImageButton back = findViewById(R.id.back);
        final Button sign_in = findViewById(R.id.sign_in);
        final Button register = findViewById(R.id.btn_register);


         fname = findViewById(R.id.firstname);
         lname = findViewById(R.id.lastname);
         phone = findViewById(R.id.contact);
         password = findViewById(R.id.password);
         spassword = findViewById(R.id.password2);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //get data from text to string values
                final String fnameText = fname.getText().toString();
                final String lnameText = lname.getText().toString();
                final String phoneText = phone.getText().toString();
                final String passwordText = password.getText().toString();
                final String spasswordText = spassword.getText().toString();

                if (!validateFname() || !validateLname() || !validatePhone() || !validatePassword()) {
                    Toast.makeText(Register.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check phone number is entered before
                            if (snapshot.hasChild(phoneText)) {
                                Toast.makeText(Register.this, "Phone number already entered", Toast.LENGTH_SHORT).show();
                            } else {
                                //send data to database
                                databaseReference.child("user").child(phoneText).child("fname").setValue(fnameText);
                                databaseReference.child("user").child(phoneText).child("lname").setValue(lnameText);
                                databaseReference.child("user").child(phoneText).child("phone").setValue(phoneText);
                                databaseReference.child("user").child(phoneText).child("password").setValue(passwordText);


                                Toast.makeText(Register.this, "User registration succesfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Second_Screen.class));
            }
        });


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });




    }
    public boolean validateFname() {
        String value = fname.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (value.isEmpty()) {
            fname.setError("Field cannot be empty");
            return false;
        } else if (value.length() >= 15) {
            fname.setError("Username too long. limit to 15 characters");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            fname.setError("White Spaces are not allowed");
            return false;
        } else {
            fname.setError(null);
            return true;
        }
    }
    public boolean validateLname() {
        String value = lname.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (value.isEmpty()) {
            lname.setError("Field cannot be empty");
            return false;
        } else if (value.length() >= 15) {
            lname.setError("Username too long. limit to 15 characters");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            lname.setError("White Spaces are not allowed");
            return false;
        } else {
            lname.setError(null);
            return true;
        }
    }

    public boolean validatePhone() {
        String value = phone.getText().toString();

        if (value.isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String value = password.getText().toString();
        String value2 = spassword.getText().toString();
        String passwords = "^" +
                //"(?=.*[0-9])"     +           //at least 1 digit
                //"(?=.*[a-z])"     +           //at least 1 lower case letter
                //"(?=.*[A-Z])"    +            //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +              //any letter
                "(?=.*[@#$%^&+=])" +            //at least 1 special character
                "(?=\\S+$)" +                   //no white spaces
                ".{2,}" +                      //at least 2 characters
                "$";

        if (value.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(passwords)) {
            password.setError("Password must have at least 1 special character, 2 numbers and letters");
            return false;
        }else if(!value.equals(value2)){
            spassword.setError("Enter the same password");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }





}

