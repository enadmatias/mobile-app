package com.example.appthesis;

public class ServiceType {
    String service;
    boolean isSelected;

    public ServiceType() {

    }

    public ServiceType(String service) {
        this.service = service;
    }



    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ServiceType " + service;
    }
}

