package com.example.appthesis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ServiceViewHolder> {
    Context context;
    ArrayList<Message> messages;
    private String sender_code = "sender";
    private String reciever_code = "receiver";



    public MessageListAdapter(ArrayList<Message> message, Context ctx) {
    this.messages = message;
    this.context = ctx;
    }
    public static class ServiceViewHolder extends RecyclerView.ViewHolder{
        TextView message_reciever, message_sender;


        ServiceViewHolder(@Nullable View itemView){
            super(itemView);
            message_reciever = itemView.findViewById(R.id.text1);
            message_sender = itemView.findViewById(R.id.text2);
        }

    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_recieved, viewGroup, false);
        ServiceViewHolder svh = new ServiceViewHolder(view);
        return svh;

    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
        final Message message = messages.get(i);
        if(sender_code.equals(messages.get(i).getMessage_code())) {
            serviceViewHolder.message_reciever.setText(messages.get(i).getMessage());
        }
        else {
            serviceViewHolder.message_reciever.setVisibility(View.GONE);
        }

        if(reciever_code.equals(messages.get(i).getMessage_code())){
            serviceViewHolder.message_sender.setText(messages.get(i).getMessage());
        }
        else{
            serviceViewHolder.message_sender.setVisibility(View.GONE);

        }

         if(reciever_code.equals(null)){
             serviceViewHolder.message_sender.setVisibility(View.VISIBLE);
             serviceViewHolder.message_reciever.setVisibility(View.GONE);
         }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
