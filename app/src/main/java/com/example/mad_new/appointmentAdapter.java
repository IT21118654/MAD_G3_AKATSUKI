package com.example.mad_new;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class appointmentAdapter extends RecyclerView.Adapter<appointmentAdapter.viewHolder>{

    ArrayList<appointmentModel> list;
    Context context;
    appointmentModel model;

    public appointmentAdapter(ArrayList<appointmentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public appointmentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item4, parent, false);
        return new appointmentAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull appointmentAdapter.viewHolder holder, int position) {
        model = list.get(position);
        holder.name.setText(model.getName());
        holder.id.setText(model.getId());
        holder.date.setText(model.getDate());
        holder.contact.setText(model.getPhone());
        holder.time.setText(model.getTime());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = holder.id.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("appointment").child(id);
                reference.removeValue();
                Intent intent = new Intent(context.getApplicationContext(), View_Appointment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(),Update_Appointment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",holder.name.getText().toString().trim());
                intent.putExtra("id",holder.id.getText().toString().trim());
                intent.putExtra("contact",holder.contact.getText().toString().trim());
                intent.putExtra("date",holder.date.getText().toString().trim());
                intent.putExtra("time",holder.time.getText().toString().trim());

                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, id, date, time,contact;
        Button btn_edit, btn_delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            id = itemView.findViewById(R.id.id);
            contact = itemView.findViewById(R.id.contact);
            btn_delete = itemView.findViewById(R.id.delete);
            btn_edit = itemView.findViewById(R.id.update);




        }
    }
}