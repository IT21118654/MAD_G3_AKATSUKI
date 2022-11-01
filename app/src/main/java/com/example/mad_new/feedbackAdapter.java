package com.example.mad_new;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class feedbackAdapter extends RecyclerView.Adapter<feedbackAdapter.viewHolder> {
    ArrayList<feedbackModel> list;
    Context context;
    feedbackModel model;

    public feedbackAdapter(ArrayList<feedbackModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public feedbackAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new feedbackAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull feedbackAdapter.viewHolder holder, int position) {
        model = list.get(position);
        holder.name.setText(model.getName());
        holder.feedback.setText(model.getFeedback());
        holder.id.setText(model.getId());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = holder.id.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("feedback").child(id);
                reference.removeValue();
                Intent intent = new Intent(context.getApplicationContext(), Feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context.getApplicationContext(),Update_feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",holder.name.getText().toString().trim());
                intent.putExtra("feedback",holder.feedback.getText().toString().trim());
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
    class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, feedback,id;
        ImageButton btn_update,btn_delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            feedback = itemView.findViewById(R.id.feedback);
            image = itemView.findViewById(R.id.profile);
            btn_update = itemView.findViewById(R.id.update);
            btn_delete = itemView.findViewById(R.id.delete);

        }
    }
}
