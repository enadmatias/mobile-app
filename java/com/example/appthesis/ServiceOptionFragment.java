package com.example.appthesis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceOptionFragment extends Fragment {
    ArrayList<ServiceType> type;
    ArrayList<String> selectedlist=new ArrayList<String>();
    private RecyclerView list;
    private Button btnGetSelected;


    public ServiceOptionFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGetSelected = view.findViewById(R.id.getSelected);
        list = (RecyclerView) view.findViewById(R.id.recyclerView_list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setHasFixedSize(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        type= new ArrayList<>();
        type.add(new ServiceType("220 Lines ( Ranges, Electric Dryers, Welders, etc.)"));
        type.add(new ServiceType("Additions & Remodeling ( Switches, Receptacles, Light Fixtures, Circuits or Entire Building)"));
        type.add(new ServiceType("Automation Controls & Systems ( Automatic Lighting)"));
        type.add(new ServiceType("Ceiling Fans ( Installation, Repair & Maintenance)"));
        type.add(new ServiceType("Electric Heat ( Installation of Baseboard Heaters)"));
        type.add(new ServiceType("Electrical Maintenance ( Inspection, Testing & Maintenance of Electrical Systems up to 480VAC)"));
        type.add(new ServiceType("Electrical Safety Inspection ( Complete Electrical System Inspection, Evaluation & Report)"));
        type.add(new ServiceType("GFCI Installation ( Kitchens, Bathrooms, Basements & Exterior Outlets)"));
        type.add(new ServiceType("Home Inspections ( Buyer / Seller House Inspection)"));


        ServiceOptionAddapter addapter = new ServiceOptionAddapter(this.type);
        list.setAdapter(addapter);
        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for (ServiceType serviceType : type) {
                    if (serviceType.isSelected()) {
                        if (stringBuilder.length() > 0)
                            stringBuilder.append(", ");
                        stringBuilder.append(serviceType.getService());
                        selectedlist.add(serviceType.getService());
                    }
                }
                //Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_LONG).show();

                Intent n=new Intent(getContext(),BookingDetails.class);
                n.putStringArrayListExtra("selected", selectedlist);
                startActivity(n);

            }
        });




    }


}
