package com.example.appthesis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class PreviewsAdapter extends RecyclerView.Adapter<PreviewsAdapter.ServiceViewHolder> {
    List<Previews> previewsList;

    public PreviewsAdapter(List<Previews> previewsList) {
        this.previewsList = previewsList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rates_reviews, viewGroup, false) ;
        PreviewsAdapter.ServiceViewHolder svh = new PreviewsAdapter.ServiceViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
        serviceViewHolder.fullname.setText(previewsList.get(i).getFullname());
        serviceViewHolder.ratingBar.setRating(previewsList.get(i).getRatings());
        serviceViewHolder.comment.setText(previewsList.get(i).getComment());
        serviceViewHolder.date.setText(previewsList.get(i).getDate());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return previewsList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView fullname, comment, date;
        RatingBar ratingBar;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.textView52);
            ratingBar = itemView.findViewById(R.id.ratingBar3);
            comment = itemView.findViewById(R.id.textView53);
            date = itemView.findViewById(R.id.textView54);

        }
    }
}
