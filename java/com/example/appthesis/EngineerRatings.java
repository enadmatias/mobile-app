package com.example.appthesis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EngineerRatings extends RecyclerView.Adapter<EngineerRatings.ViewHolder> {
    private List<Previews> list;
    Context context;


    public EngineerRatings(List<Previews> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rates_and_reviews, viewGroup, false) ;
        EngineerRatings.ViewHolder vh = new EngineerRatings.ViewHolder(view);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.fullname.setText(list.get(i).getFullname());
        viewHolder.rb.setRating(list.get(i).getRatings());
        viewHolder.comment.setText(list.get(i).getComment());
        viewHolder.date.setText(list.get(i).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname, comment, date;
        RatingBar rb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          fullname = itemView.findViewById(R.id.textView74);
          rb = itemView.findViewById(R.id.ratingBar4);
          comment = itemView.findViewById(R.id.textView72);
          date = itemView.findViewById(R.id.textView73);
        }
    }
}
