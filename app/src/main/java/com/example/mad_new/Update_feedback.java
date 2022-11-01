package com.example.mad_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_feedback extends AppCompatActivity {

    String name, id, feedback;
    EditText txt_name, txt_id, txt_feedback;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_feedback);

        name = getIntent().getStringExtra("name");
        feedback=getIntent().getStringExtra("feedback");
        id = getIntent().getStringExtra("id");

        btn_update=findViewById(R.id.update);
        txt_name = findViewById(R.id.name);
        txt_feedback=findViewById(R.id.feedback_text);
        txt_id = findViewById(R.id.id);

        txt_id.setText(id);
        txt_feedback.setText(feedback);
        txt_name.setText(name);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");
        final EditText id = findViewById(R.id.id);
        final EditText name = findViewById(R.id.name);
        final EditText feedback =findViewById(R.id.feedback_text);
        final Button update = findViewById(R.id.update);
        final ImageButton back = findViewById(R.id.back);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idText = id.getText().toString();
                final String nameText = name.getText().toString();
                final String feedbackText = feedback.getText().toString();

                if(feedbackText.isEmpty()){
                    feedback.setError("Feedback is empty");
                    feedback.requestFocus();
                    return;
                }else{
                    databaseReference.child("feedback").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("feedback").child(idText).child("id").setValue(idText);
                            databaseReference.child("feedback").child(idText).child("feedback").setValue(feedbackText);
                            databaseReference.child("feedback").child(idText).child("name").setValue(nameText);
                            Toast.makeText(Update_feedback.this, "Feedback add successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Update_feedback.this,Feedback.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });



    }
}