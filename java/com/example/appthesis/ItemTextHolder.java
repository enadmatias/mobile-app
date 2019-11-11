package com.example.appthesis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemTextHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public ItemTextHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.list_item);
    }
    public void bind(String text) {
        textView.setText(text);
    }

}
