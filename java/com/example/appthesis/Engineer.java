package com.example.appthesis;

public class Engineer {

    public  String id;
    public  String fullname;
    public String address;
    public String email;
    public String contactNo;
    public String yrs_exp;

    public Engineer(String id, String fullname, String address, String email, String contactNo, String yrs_exp) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.contactNo = contactNo;
        this.yrs_exp = yrs_exp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getYrs_exp() {
        return yrs_exp;
    }

    public void setYrs_exp(String yrs_exp) {
        this.yrs_exp = yrs_exp;
    }
}
