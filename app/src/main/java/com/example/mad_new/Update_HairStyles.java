package com.example.mad_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_HairStyles extends AppCompatActivity {


    String name, id, price, img_uri;
    EditText txt_sname, txt_sid, txt_price, txt_image;
    Button btn_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hair_styles);
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        price = getIntent().getStringExtra("price");
        img_uri = getIntent().getStringExtra("imageUri");
        btn_update=findViewById(R.id.btn_update);
        txt_image = findViewById(R.id.image);
        txt_sname = findViewById(R.id.sname);
        txt_sid = findViewById(R.id.sid);
        txt_price = findViewById(R.id.price);
        txt_image.setText(img_uri);
        txt_sid.setText(id);
        txt_price.setText(price);
        txt_sname.setText(name);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://stylecitizen123-default-rtdb.firebaseio.com/");

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idText = txt_sid.getText().toString();
                final String nameText = txt_sname.getText().toString();
                final String priceText = txt_price.getText().toString();
                final String imageText = txt_image.getText().toString();

                if (idText.isEmpty()) {
                    txt_sid.setError("Id is empty");
                    txt_sid.requestFocus();
                    return;
                } else if (nameText.isEmpty()) {
                    txt_sname.setError("Name is empty");
                    txt_sname.requestFocus();
                    return;
                } else if (priceText.isEmpty()) {
                    txt_price.setError("Price is empty");
                    txt_price.requestFocus();
                    return;
                } else if (imageText.isEmpty()) {
                    txt_image.setError("Image is empty");
                    txt_image.requestFocus();
                    return;
                } else {
                    databaseReference.child("styles").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("styles").child(idText).child("id").setValue(idText);
                            databaseReference.child("styles").child(idText).child("name").setValue(nameText);
                            databaseReference.child("styles").child(idText).child("price").setValue(priceText);
                            databaseReference.child("styles").child(idText).child("image").setValue(imageText);
                            Toast.makeText(getApplicationContext(), "Hairstyle add Successfully !!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), Hair_Styles.class);
                            startActivity(intent);

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