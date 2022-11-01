package com.example.mad_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theophrast.ui.widget.SquareImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class hairstyleAdapter extends RecyclerView.Adapter<hairstyleAdapter.viewHolder> {

    ArrayList<hairstyleModel> list;
    Context context;
    hairstyleModel model;

    public hairstyleAdapter(ArrayList<hairstyleModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        model = list.get(position);
        holder.name.setText(model.getName() + "");
        holder.price.setText(model.getPrice() + "");

        InputStream in = null;
        try {
            in = new URL(model.getImage().trim()).openStream();
            Bitmap bimage = BitmapFactory.decodeStream(in);
            holder.image.setImageBitmap(bimage);

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.id.setText(model.getId());
        holder.image_uri.setText(model.getImage().trim());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = holder.id.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("styles").child(id);
                reference.removeValue();
                Intent intent = new Intent(context.getApplicationContext(), Hair_Styles.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
            });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(),Update_HairStyles.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",holder.name.getText().toString().trim());
                intent.putExtra("price",holder.price.getText().toString().trim());
                intent.putExtra("imageUri",holder.image_uri.getText().toString().trim());
                intent.putExtra("id",holder.id.getText().toString().trim());
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @NonNull

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, id,image_uri;
        Button btn_edit, btn_delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            id = itemView.findViewById(R.id.tst_style_id);
            image_uri=itemView.findViewById(R.id.image_uri);


        }
    }
}
