package com.example.appthesis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ElectricianAdapter extends RecyclerView.Adapter<ElectricianAdapter.ViewHolder> {
    SessionID sessionID;
    private List<Electrician> electricians;
    Context context;

    public ElectricianAdapter(List<Electrician> electricians, Context context) {
        this.electricians = electricians;
        this.context = context;
        this.sessionID = new SessionID(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.electrician_items, viewGroup, false) ;
        ElectricianAdapter.ViewHolder vh = new ElectricianAdapter.ViewHolder(view);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.fullname.setText(electricians.get(i).getFullname());

        viewHolder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                String id = electricians.get(position).getId();
                sessionID.createSession(id);
                Toast.makeText(v.getContext(), "ID " + id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(), ServiceOption.class);
                i.putExtra("id", id);
                context.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {
        return electricians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        Button btn_view, btn_book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.textView36);
            btn_view = itemView.findViewById(R.id.button19);
            btn_book = itemView.findViewById(R.id.button20);
        }
    }
}
