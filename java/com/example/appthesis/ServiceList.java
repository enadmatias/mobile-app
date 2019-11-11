package com.example.appthesis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ServiceList extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    private ArrayList<ServiceType> services;
    ArrayList<String> list =new ArrayList<>();
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        proceed = findViewById(R.id.button12);
        recyclerView = findViewById(R.id.recyclerView5);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        initializeData();
        initializeAdapter();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("string_array"));



    }



    private void initializeData() {
        services = new ArrayList<ServiceType>();
        services.add(new ServiceType("220 Lines ( Ranges, Electric Dryers, Welders, etc.)"));
        services.add(new ServiceType("Additions & Remodeling ( Switches, Receptacles, Light Fixtures, Circuits or Entire Building)"));
        services.add(new ServiceType("Automation Controls & Systems ( Automatic Lighting)"));
        services.add(new ServiceType("Ceiling Fans ( Installation, Repair & Maintenance)"));
        services.add(new ServiceType("Electric Heat ( Installation of Baseboard Heaters)"));
        services.add(new ServiceType("Electrical Maintenance ( Inspection, Testing & Maintenance of Electrical Systems up to 480VAC)"));
        services.add(new ServiceType("Electrical Safety Inspection ( Complete Electrical System Inspection, Evaluation & Report)"));
        services.add(new ServiceType("GFCI Installation ( Kitchens, Bathrooms, Basements & Exterior Outlets)"));
        services.add(new ServiceType("Home Inspections ( Buyer / Seller House Inspection)"));
        services.add(new ServiceType("Lighting – Interior / Exterior ( Installation, Repair & Maintenance of Fixtures & Wiring )"));
        services.add(new ServiceType("Lighting – Landscape ( Installation, Repair & Maintenance of Fixtures & Wiring )"));
        services.add(new ServiceType("Lighting – Parking Lot ( Installation, Repair & Maintenance of Fixtures & Wiring )"));
        services.add(new ServiceType("Lighting – Retrofits ( Replacement & Upgrading of Lighting Systems for greater Efficiency)"));
        services.add(new ServiceType("Network Systems ( Installation of Data Wiring & Components)"));
        services.add(new ServiceType("Panel Replacements ( Replace Fuse or old breaker panels with new Circuit Breaker Panels)"));
        services.add(new ServiceType("Phone & Data Outlets ( Installation, Repair & Maintenance)"));
        services.add(new ServiceType("Receptacles & Switches ( Replacement of Switches, Receptacles & other Devices)"));
        services.add(new ServiceType("Rewiring ( Replacement of part or all existing wiring with new updated wiring)"));
        services.add(new ServiceType("Service Upgrades ( Replace old 60 Amp Services with 100 Amp or higher Services)"));
        services.add(new ServiceType("Smoke Detectors ( Installations)"));
        services.add(new ServiceType("Trouble Shooting ( General Trouble Shooting of Electrical System problems)"));
        services.add(new ServiceType("Violation Corrections ( Correct Violations found during Local inspections)"));

    }
    private void initializeAdapter() {
        ServiceListAdapter adapter = new ServiceListAdapter(services, this);
        recyclerView.setAdapter(adapter);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("string_array");
            Bundle extras = getIntent().getExtras();
            if(extras!=null)
            {
                list = extras.getStringArrayList("string_array");
            }
            for(int i=0; i<list.size(); i++){
                System.out.print("Services \n" + list.get(i).toString());
            }
        }
    };

}
