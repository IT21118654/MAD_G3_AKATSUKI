package com.example.mad_new;

public class appointmentModel {
    String id;
    String time;
    String date;
    String name;
    String phone;

    public appointmentModel(){
        
    }

    public appointmentModel(String id, String time, String date, String name, String phone) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.name = name;
        this.phone = phone;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
