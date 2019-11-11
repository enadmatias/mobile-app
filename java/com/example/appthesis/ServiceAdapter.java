package com.example.appthesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
 List<ServiceType> services;
 Context context;
 int lastSelectedPosition = -1;
 SessionManager sessionManager;
 ServiceType serviceType;


    public ServiceAdapter(List<ServiceType> services, Context ctx) {
        this.services = services;
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_service, viewGroup, false) ;
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text.setText(services.get(i).getService());
        viewHolder.selectionState.setChecked(lastSelectedPosition == i);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       RadioButton selectionState;
       TextView text;
       String type;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
             selectionState = itemView.findViewById(R.id.radioButton9);
             text = itemView.findViewById(R.id.textView48);
             selectionState.setOnClickListener(new View.OnClickListener() {

                 @Override
                 public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                      type = text.getText().toString();
                    //Toast.makeText(ServiceAdapter.this.context, "The service you choose "+ text.getText(), Toast.LENGTH_SHORT).show();
                     System.out.println("The service you choose " + text.getText());
                     Intent intent = new Intent("custom-message");
                     intent.putExtra("type",type);
                     LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                     //String type = text.getText().toString();

                 }
             });
        }

    }

}
