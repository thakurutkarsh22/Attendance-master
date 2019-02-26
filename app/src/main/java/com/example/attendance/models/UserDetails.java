package com.example.attendance.models;

public class UserDetails {

    String ID;
    String Name;

    public UserDetails(String id, String name) {
        ID = id;
        Name = name;
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
