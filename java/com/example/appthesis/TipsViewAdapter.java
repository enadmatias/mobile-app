package com.example.appthesis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TipsViewAdapter extends RecyclerView.Adapter<ItemTextHolder> {
    String[] items;

    public TipsViewAdapter(String[] items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemTextHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_item, viewGroup, false);
        return new ItemTextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTextHolder itemTextHolder, int i) {
        itemTextHolder.bind(items[i]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
    public long getItemId(int position) {
        return position;
    }
}
