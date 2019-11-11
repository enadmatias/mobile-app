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

public class EngineerAdapter extends RecyclerView.Adapter<EngineerAdapter.ViewHolder> {
    ArrayList<String> newlist =new ArrayList<>();
   private List<Engineer> list;
   Context context;
   SessionID sessionID;

    public EngineerAdapter(List<Engineer> list, Context context) {
        this.list = list;
        this.context = context;
        this.sessionID = new SessionID(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.engineer_items, viewGroup, false) ;
        ViewHolder vh = new ViewHolder(view);
        return  vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.fullname.setText(list.get(i).getFullname());

        viewHolder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                String id = list.get(position).getId();
                sessionID.createSession(id);
                Toast.makeText(v.getContext(), "ID " + id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(), ServiceOption.class);
                i.putExtra("id", id);
                context.startActivity(i);

            }
        });
        viewHolder.details.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getLayoutPosition();
                        String id = list.get(position).getId();
                        Toast.makeText(v.getContext(), "ID " + id, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(), EngineerDetails.class);
                        i.putExtra("id", id);
                        context.startActivity(i);

                    }
                }
        );
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView fullname;
      Button details, book;
      String book_id;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.textView64);
            details = itemView.findViewById(R.id.button15);
            book = itemView.findViewById(R.id.button16);



        }
    }
}
