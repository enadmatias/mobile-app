package com.example.appthesis;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    ArrayList<ServiceType> list = new ArrayList<>();
    ArrayList<String> selectedlist = new ArrayList<>();
    Context context;
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    SparseBooleanArray sba = new SparseBooleanArray();
    String data = "";
    StringBuffer sb=new StringBuffer();
    private boolean isChecked;

    public ServiceListAdapter(ArrayList<ServiceType> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_service_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.bind(i);
        final ServiceType serviceType = list.get(i);
        viewHolder.checkBox.setText(list.get(i).getService());
        viewHolder.checkBox.setChecked(serviceType.isSelected());
        viewHolder.checkBox.setTag(list.get(i));

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                ServiceType serviceType1 = (ServiceType) viewHolder.checkBox.getTag();
               serviceType1.setSelected(viewHolder.checkBox.isChecked());
                list.get(i).setSelected(viewHolder.checkBox.isChecked());
                if (!itemStateArray.get(adapterPosition, false)) {
                    viewHolder.checkBox.setChecked(true);
                    itemStateArray.put(adapterPosition, true);

                    /*for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).isSelected() && !data.equals(list.get(i).getService())) {
                            data = data + "\n" + list.get(j).getService().toString();
                            selectedlist.add(data);
                        }
                    }*/

                   /* for(int j=0; j<itemStateArray.size();j++){
                        if(itemStateArray.valueAt(j)){
                            sb.append(list.get(itemStateArray.keyAt(j)));
                            //selectedlist.add(list.get(itemStateArray.keyAt(j)));
                            if(i<itemStateArray.size()-1)
                                sb.append(",");

                        }
                    }*/
                    selectedlist.add(list.get(i).getService());
                    Toast.makeText(context, " \n" + list.get(i).getService(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("string_array");
                    intent.putStringArrayListExtra("selected",selectedlist);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                }
                else  {
                    viewHolder.checkBox.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void loadItems(ArrayList<ServiceType> service) {
        this.list = service;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        Button proceed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox3);
            this.setIsRecyclable(false);

            //checkBox.setOnClickListener(this);

         /*  checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).isSelected()) {
                                data = data + "\n" + list.get(j).getService().toString();

                            }
                        }

                        Toast.makeText(context, " " + data, Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }
                }
            });*/





           /*checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    String data = "";
                    if (!itemStateArray.get(adapterPosition, false)) {
                        checkBox.setChecked(true);
                        itemStateArray.put(adapterPosition, true);
                        for(int j=0; j<list.size(); j++){
                            if(list.get(j).isChecked){
                              data = data + "\n" + list.get(j).getService().toString();
                              Toast.makeText(context, "Selected service " + data, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else  {
                        checkBox.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                    }
                }
            });
        }*/

        }

        public void bind(int i) {
            checkBox.setText(list.get(i).getService());
            if (!itemStateArray.get(i, false)) {
                checkBox.setChecked(false);}
            else {
                checkBox.setChecked(true);
            }
        }
    }
    }

