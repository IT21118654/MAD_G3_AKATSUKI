package com.example.mad_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class My_feedback extends AppCompatActivity {
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        final EditText feedback = findViewById(R.id.feedback_text);
        final EditText name = findViewById(R.id.name);
        final ImageButton back = findViewById(R.id.back);
        final Button submit = findViewById(R.id.submit_btn);
        Intent intent1 =getIntent();
        String id = databaseReference.push().getKey();
        String username =intent1.getStringExtra("name");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String feedText = feedback.getText().toString();
                final String nameTxt = name.getText().toString();
                String fid = id;





                if(feedText.isEmpty()){
                    feedback.setError("Feedback is empty");
                    feedback.requestFocus();
                    return;
                }else{
                    databaseReference.child("feedback").child(fid).child("id").setValue(id);
                    databaseReference.child("feedback").child(fid).child("feedback").setValue(feedText);
                    databaseReference.child("feedback").child(fid).child("name").setValue(nameTxt);
                    Toast.makeText(My_feedback.this, "Feedback add successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(My_feedback.this,home.class));
                    finish();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_feedback.this,Feedback.class));
            }
        });

    }
}