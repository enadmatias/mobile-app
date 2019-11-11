package com.example.appthesis;


import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ServiceViewHolder> {
    List<Service> service;


    public RecyclerViewAdapter(List<Service> service) {
        this.service = service;
    }



    static  class ServiceViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView serviceLogo;

         ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            serviceLogo = itemView.findViewById(R.id.imageView6);
        }
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout, viewGroup, false) ;
        ServiceViewHolder svh = new ServiceViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
       serviceViewHolder.serviceLogo.setImageResource(service.get(i).getLogoId());
    }

    @Override
    public int getItemCount() {
        return service.size();
    }

}
