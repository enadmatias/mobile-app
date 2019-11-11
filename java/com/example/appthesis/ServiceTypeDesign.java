package com.example.appthesis;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeDesign extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type_design);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(services);
        initializeData();
        initializeAdapter();


    }

    private void initializeData(){
        services = new ArrayList<>();
        services.add(new Service(R.drawable.wire_install));
        services.add(new Service(R.drawable.circuit_breaker_repair));
        services.add(new Service(R.drawable.circuit_repair));
        services.add(new Service(R.drawable.troubleshooting));
        services.add(new Service(R.drawable.rewiring));
    }

    private void initializeAdapter(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(services);
        recyclerView.setAdapter(adapter);
    }

}

